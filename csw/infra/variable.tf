variable "vpc_id" {
  description = "ID da VPC onde o ECS será criado"
  type        = string
}

variable "subnet_id" {
  description = "ID do subnet público"
  type        = string
}

variable "execution_role_arn" {
  description = "ARN da role de execução do ECS (ex: LabRole)"
  type        = string
}