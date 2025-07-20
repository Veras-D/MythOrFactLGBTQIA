output "public_ip" {
  value = aws_instance.myth_or_fact_server.public_ip
}

output "public_dns" {
  value = aws_instance.myth_or_fact_server.public_dns
}