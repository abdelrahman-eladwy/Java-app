# Secure Spring Boot Application
#eld

## Overview

This is a comprehensive enterprise-grade Spring Boot application designed to demonstrate security best practices and integrate with modern CI/CD pipelines featuring extensive security scanning tools including Fortify ScanCentral (SAST & DAST), Sonatype Nexus IQ, Trivy, Anchore, and more.

## 🏗️ Architecture

### Application Stack
- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Database**: MySQL (Production), H2 (Development)
- **Caching**: Redis
- **Secret Management**: HashiCorp Vault
- **Container Runtime**: Docker
- **Orchestration**: Kubernetes (EKS/Minikube)

### Security Features
- Spring Security with JWT authentication
- Input validation with Jakarta Bean Validation
- Secure password encoding (BCrypt)
- CORS configuration
- Actuator endpoints with role-based access
- Security headers and best practices

## 📋 Prerequisites

### Development Environment
- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose
- kubectl
- Minikube (for local testing) or AWS EKS access

### CI/CD Tools
- Jenkins (for Jenkins pipeline)
- Tekton (for Tekton pipeline)
- ArgoCD (for GitOps deployment)

### Security Scanning Tools
- Fortify ScanCentral (SAST & DAST)
- Sonatype Nexus IQ
- Trivy
- Anchore
- Hadolint
- Kube-bench
- Falco

## 🚀 Quick Start

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/secure-spring-app.git
   cd secure-spring-app
   ```

2. **Build the application**
   ```bash
   mvn clean package
   ```

3. **Run with H2 database (default)**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console
   - Actuator Health: http://localhost:8080/actuator/health

### Docker Deployment

1. **Build Docker image**
   ```bash
   docker build -t secure-spring-app:latest .
   ```

2. **Run with Docker Compose**
   ```bash
   docker-compose up -d
   ```

### Kubernetes Deployment (Minikube)

1. **Start Minikube**
   ```bash
   minikube start --cpus=4 --memory=8192
   ```

2. **Apply Kubernetes manifests**
   ```bash
   kubectl apply -f k8s/namespace.yaml
   kubectl apply -f k8s/secrets.yaml
   kubectl apply -f k8s/configmap.yaml
   kubectl apply -f k8s/mysql.yaml
   kubectl apply -f k8s/redis.yaml
   kubectl apply -f k8s/deployment.yaml
   kubectl apply -f k8s/service.yaml
   ```

3. **Access the application**
   ```bash
   kubectl get svc -n secure-spring-app
   minikube service secure-spring-app-lb -n secure-spring-app
   ```

## 🔐 Security Scanning

### Fortify ScanCentral SAST
```bash
# Package application
scancentral package -bt mvn -o package.zip

# Submit scan
scancentral start \
  -sscurl https://fortify-ssc.example.com \
  -ssctoken YOUR_TOKEN \
  -upload \
  -application "secure-spring-app" \
  -version "1.0.0"
```

### Sonatype Nexus IQ
```bash
nexus-iq-cli \
  -s https://nexus-iq.example.com \
  -a username:password \
  -i secure-spring-app \
  -t build \
  .
```

### Trivy Image Scan
```bash
trivy image --severity HIGH,CRITICAL secure-spring-app:latest
```

### Hadolint
```bash
docker run --rm -i hadolint/hadolint < Dockerfile
```

## 🔄 CI/CD Pipelines

### Jenkins Pipeline

The Jenkinsfile includes all 20 stages:
1. Checkout
2. Hadolint - Dockerfile Lint
3. Fortify ScanCentral SAST
4. Sonatype Nexus IQ - SCA
5. Maven Build & Test
6. OWASP Dependency Check
7. Build Docker Image
8. Trivy Image Scan
9. Anchore Image Scan
10. Push to ECR
11. Retrieve Secrets from Vault
12. Kube-bench Compliance
13. Update GitOps Repo
14. Deploy to EKS (via ArgoCD)
15. Wait for Deployment
16. Fortify DAST Scan
17. Falco Runtime Security
18. Upload Reports to S3
19. CloudWatch Logs
20. Security Hub Integration

**Run Jenkins Pipeline:**
```bash
# Configure Jenkins credentials
# - fortify-token
# - nexus-iq-credentials
# - vault-token
# - aws-credentials

# Trigger pipeline via webhook or manually
```

### Tekton Pipeline

The Tekton pipeline includes similar stages optimized for Kubernetes-native execution.

**Install Tekton:**
```bash
kubectl apply -f https://storage.googleapis.com/tekton-releases/pipeline/latest/release.yaml
```

**Deploy pipeline:**
```bash
kubectl apply -f tekton/tasks/
kubectl apply -f tekton/pipeline.yaml
kubectl apply -f tekton/pipelinerun.yaml
```

**Monitor execution:**
```bash
tkn pipelinerun logs secure-spring-app-pipeline-run -f -n secure-spring-app
```

## 📊 API Endpoints

### Public Endpoints
- `GET /api/public/health` - Health check
- `GET /api/public/info` - Application information

### Product APIs (Authenticated)
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/active` - Get active products
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?keyword=` - Search products
- `POST /api/products` - Create product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### User APIs (Admin Role Required)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Actuator Endpoints (Admin Role)
- `GET /actuator/health` - Health status
- `GET /actuator/metrics` - Application metrics
- `GET /actuator/prometheus` - Prometheus metrics

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Run Integration Tests
```bash
mvn verify
```

### Code Coverage
```bash
mvn clean test jacoco:report
# Report available at target/site/jacoco/index.html
```

## 🔧 Configuration

### Environment Variables

**Development:**
- `SPRING_PROFILES_ACTIVE=dev`

**Production:**
- `SPRING_PROFILES_ACTIVE=prod`
- `DB_URL` - Database connection URL
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password (from Vault)
- `REDIS_HOST` - Redis host
- `REDIS_PORT` - Redis port
- `VAULT_HOST` - HashiCorp Vault host
- `VAULT_TOKEN` - Vault authentication token

### HashiCorp Vault Integration

The application integrates with Vault for secret management:

```bash
# Store database credentials
vault kv put secret/database \
  username=appuser \
  password=securepassword

# Store API keys
vault kv put secret/api-keys \
  fortify-token=YOUR_TOKEN \
  nexus-token=YOUR_TOKEN
```

## 📦 Project Structure

```
secure-spring-app/
├── src/
│   ├── main/
│   │   ├── java/com/security/app/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── repository/      # Data repositories
│   │   │   ├── service/         # Business logic
│   │   │   └── SecureSpringApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
│   └── test/                    # Unit and integration tests
├── k8s/                         # Kubernetes manifests
│   ├── namespace.yaml
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   ├── secrets.yaml
│   ├── mysql.yaml
│   ├── redis.yaml
│   └── ingress.yaml
├── tekton/                      # Tekton pipeline definitions
│   ├── pipeline.yaml
│   ├── pipelinerun.yaml
│   └── tasks/
├── Dockerfile                   # Docker build configuration
├── Dockerfile.secure            # Hadolint-compliant Dockerfile
├── Jenkinsfile                  # Jenkins pipeline
├── pom.xml                      # Maven configuration
└── README.md
```

## 🛡️ Security Best Practices Implemented

1. **Shift-Left Security**: SAST and SCA during build phase
2. **Container Security**: Multi-stage builds, non-root users, minimal base images
3. **Secrets Management**: HashiCorp Vault integration
4. **Runtime Security**: Falco monitoring, OPA Gatekeeper policies
5. **Compliance**: Kube-bench CIS benchmarks, Hadolint checks
6. **Image Scanning**: Trivy and Anchore for vulnerability detection
7. **Dynamic Testing**: Fortify DAST post-deployment
8. **Audit Trail**: CloudTrail, CloudWatch, S3 report storage
9. **Zero Trust**: Network policies, RBAC, Pod Security Standards

## 📈 Monitoring & Observability

### Prometheus Metrics
Access Prometheus-compatible metrics at `/actuator/prometheus`

### CloudWatch Integration
- Application logs forwarded to CloudWatch
- Custom metrics for business KPIs
- Alarms configured for critical events

### Falco Runtime Security
Monitor suspicious runtime behavior:
```bash
kubectl logs -l app=falco -n falco --tail=100
```

## 🌐 AWS Services Integration

- **EKS**: Kubernetes cluster management
- **ECR**: Container registry
- **RDS**: MySQL database
- **S3**: Report storage and backups
- **CloudWatch**: Logging and monitoring
- **GuardDuty**: Threat detection
- **Security Hub**: Centralized security findings
- **CloudTrail**: API audit logging

## 🔗 ArgoCD GitOps Deployment

1. **Install ArgoCD**
   ```bash
   kubectl create namespace argocd
   kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
   ```

2. **Create Application**
   ```bash
   argocd app create secure-spring-app \
     --repo https://github.com/your-org/gitops-repo \
     --path secure-spring-app \
     --dest-server https://kubernetes.default.svc \
     --dest-namespace secure-spring-app \
     --sync-policy automated
   ```

## 💰 AWS Cost Estimation (Free Tier vs Paid)

### Free Tier (First 12 Months)
- **EKS Control Plane**: $0.10/hour = ~$73/month
- **EC2 t3.medium (2 nodes)**: Free tier 750 hours/month
- **RDS db.t3.micro**: Free tier 750 hours/month
- **S3**: 5GB free storage
- **CloudWatch**: 10 custom metrics free
- **Total**: ~$73-100/month (mainly EKS control plane)

### Minikube Alternative (Free)
For POC purposes, use Minikube locally:
- **Cost**: $0 (runs on your machine)
- **Suitable for**: Development and testing

## 📝 License

This project is licensed under the MIT License.

## 👥 Contributors

- Security Team
- DevOps Team
- Development Team



## 🎯 Next Steps

1. Configure Fortify SSC and ScanCentral
2. Set up Sonatype Nexus IQ Server
3. Deploy to EKS or set up Minikube
4. Configure ArgoCD for GitOps
5. Set up HashiCorp Vault
6. Configure Jenkins with required credentials
7. Install Tekton on Kubernetes cluster
8. Deploy Falco for runtime security
9. Configure AWS Security Hub integration
10. Set up S3 bucket for security reports
# Java-app
