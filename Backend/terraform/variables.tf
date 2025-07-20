variable "region" {
  description = "AWS region to deploy resources"
  type        = string
}

variable "key_name" {
  description = "SSH key name"
  type        = string
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
}