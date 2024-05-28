## Subscribing SQS Queue to an SNS Topic

Subscribing our SQS queue to our SNS topic. 
This will allow the messages published to the SNS topic user-account-created to automatically be forwarded to the SQS queue dispatch-email-notification for consumption by our subscriber microservice.
`aws-iam-policy.json`

## Resource Based Policy
Resource-based policies are attached to an AWS resource (SQS in this context).
Create a resource-based policy on the SQS queue to allow the SNS topic to send messages to it. 

`aws-resource-policy.json`

## Encryption at Rest Using KMS
1. Create a custom symmetric AWS KMS key.
2. Enable encryption on both our SNS topic and SQS queue by configuring them to use our newly created KMS key.

`aws-kms-resource-policy.json`

## Payroll Sample Project - AWS Lambda in Spring Boot

```bash
./mvnw clean package spring-boot:build-image
```

```bash
sudo docker-compose up -d
```
