# Hello Boot

A simple Spring-Boot RESTful API with an Oracle DB backend.

## Required tools

1. OpenJDK 23
2. A suitable Java IDE. I used IntelliJ Idea but any well-built Java IDE can fit.
3. A tool for testing REST APIs like [Postman](https://www.postman.com/) or the
[Talend API Tester](https://chromewebstore.google.com/detail/talend-api-tester-free-ed/aejoelaoggembcahagimdiliamlcdmfm)
browser plugin. Some IDEs like [IntelliJ](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html)
and [VS Code](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) have integrated REST clients.

## Development resources

The project is configured to interact with an Oracle database running in a Docker container.<br>
Scripts are provided in the _deployment_ subdirectory for creating and launching all the necessary containers.

To launch the Oracle server open a command prompt on the _deployment\\docker\\oracle-database-free_ subdirectory and
execute the _install-oracle-on-docker.bat_ script.

**Notes**:

* The first time that command is executed Oracle will take some time to complete its setup but further startup will be quicker.
* [DbGate](https://dbgate.org/) a powerful and free database client is included in the setup. It listens on [port 3100](http://localhost:3100).

## Generating test data

You can enable test data generation any time you build or run the application by setting the _management.data.generation.enabled_.
property (in _application.yml_) to **true**. This should be done only once to prevent the same data from being generated
over and over again. After your first build or runm you should turn that property back to **false**.

## Deployment

There are also scripts for deploying the application on Docker or Kubernetes. It's assumed you have a Docker engine
and a Kubernetes cluster running on your local machine. [Docker desktop](https://docs.docker.com/desktop/) supplies both resources.

To deploy the application on Docker first build it, then open a command prompt on the _deployment\\docker\\application_
subdirectory and execute the _deploy-app-on-docker.bat_ script.

To deploy the application in a Kubernetes cluster, build it first, then open a command prompt on the _deployment\\kubernetes_
subdirectory and execute the _deploy-app-on-k8s-cluster.bat_ script.
