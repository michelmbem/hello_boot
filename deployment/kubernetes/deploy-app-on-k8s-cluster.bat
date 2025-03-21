@echo off

echo "Creating a local container registry..."
docker run -d -p 5000:5000 --restart always --name registry registry:2
echo "----------------------------------------------------------------"

echo "Building the project's image..."
copy ..\..\build\libs\hello-boot-1.0.war ..\docker\application
if %errorlevel% neq 0 goto error
docker build -t localhost:5000/hello-boot:1.0 ..\docker\application
if %errorlevel% neq 0 goto error
echo "----------------------------------------------------------------"

echo "Deploying in local container registry..."
docker push localhost:5000/hello-boot:1.0
if %errorlevel% neq 0 goto error
echo "----------------------------------------------------------------"

echo "Creating a k8s deployment..."
kubectl apply -f k8s-deployment.yml
if %errorlevel% neq 0 goto error
echo "----------------------------------------------------------------"

echo "Creating a k8s service..."
kubectl apply -f k8s-service.yml
if %errorlevel% neq 0 goto error
echo "----------------------------------------------------------------"

echo "Checking the deployment..."
kubectl get deployment hello-boot
echo "----------------------------------------------------------------"

echo "Checking the service..."
kubectl get service hello-boot
echo "----------------------------------------------------------------"

del ..\docker\application\*.war
echo "Done"
exit

error:
del ..\docker\application\*.war
exit /b %errorlevel%
