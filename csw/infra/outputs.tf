output "ecr_repository_url" {
  value = aws_ecr_repository.my_java_app.repository_url
}
output "ecs_cluster_name" {
  value = aws_ecs_cluster.my_java_app_cluster.name
}
output "ecs_service_name" {
  value = aws_ecs_service.my_java_app_service.name
}