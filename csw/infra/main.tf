provider "aws" {
  region = "us-east-1"
}

resource "aws_ecr_repository" "my_java_app" {
  name = "my-java-app"
}

resource "aws_ecs_cluster" "my_java_app_cluster" {
  name = "my-java-app-cluster"
}

resource "aws_security_group" "ecs_sg" {
  name        = "ecs-sg"
  description = "Enable HTTP access"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_ecs_task_definition" "my_java_app_task" {
  family                   = "my-java-app-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = var.execution_role_arn

  container_definitions = jsonencode([
    {
      name      = "my-java-app"
      image     = "public.ecr.aws/docker/library/hello-world"
      essential = true
      portMappings = [
        {
          containerPort = 8080
          protocol      = "tcp"
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "my_java_app_service" {
  name            = "my-java-app-service"
  cluster         = aws_ecs_cluster.my_java_app_cluster.id
  task_definition = aws_ecs_task_definition.my_java_app_task.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets          = [var.subnet_id]
    security_groups  = [aws_security_group.ecs_sg.id]
    assign_public_ip = true
  }
}