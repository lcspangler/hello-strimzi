# hello-strimzi

A Strimzi hello world example demonstrating Kafka:
- Producer
- Stream
- Consumer
- Stream Queries

Guide includes local Minishift environment setup instructions. 

The application builds using s2i with the Wildfly - CentOS docker image, but the official Jboss EAP 7 image and OpenShift can be substituted. 

References:
- https://docs.okd.io/latest/minishift/getting-started/
- http://strimzi.io/quickstarts/okd/
- https://blog.osninja.io/source-to-image-part-one/
- https://github.com/openshift-s2i/s2i-wildfly/blob/master/README.md
- https://github.com/openshift/openshift-jee-sample.git
- https://github.com/jboss-developer/jboss-eap-quickstarts/tree/7.1/spring-resteasy


## Manual Setup

#### OC and Minishift

Follow the Minishift install instructions according to your environment:

https://docs.okd.io/latest/minishift/getting-started/preparing-to-install.html

Install the most recent `oc` binary:

https://github.com/openshift/origin/releases 

Ensure that Minishift and `oc` versions are aligned:
```
$ oc version
oc v3.11.0+0cbc58b
kubernetes v1.11.0+d4cacc0
features: Basic-Auth

Server https://192.168.64.6:8443
kubernetes v1.11.0+d4cacc0
```

#### Strimzi Cluster and Kafka Topics

Start minishift:
```
$ minishift start
```

Once the Kubernetes cluster is running, login as admin user:
```
$ oc login -u system:admin
```

Create a new project for the Strimzi cluster:
```
$ oc new-project kafka-cluster
```

Apply the Strimzi installation file:
```
$ oc apply -f kafka/strimzi-cluster-operator-0.8.2.yaml -n kafka-cluster
```

Provision an ephemeral Kafka cluster:
```
$ oc apply -f kafka/kafka-ephemeral.yaml -n kafka-cluster
```

Note: The above files are local versions of these Strimzi project examples, modified to use the project name 'kafka-cluster':
- https://github.com/strimzi/strimzi-kafka-operator/releases/download/0.8.2/strimzi-cluster-operator-0.8.2.yaml
- https://github.com/strimzi/strimzi-kafka-operator/blob/master/examples/kafka/kafka-ephemeral.yaml


Watch the deployment until all Kafka pods are created and running:
```
$ oc get pods -n kafka-cluster -w
NAME                                          READY     STATUS    RESTARTS   AGE
my-cluster-entity-operator-5d7cd7774c-x8sg7   3/3       Running   0          33s
my-cluster-kafka-0                            2/2       Running   0          57s
my-cluster-kafka-1                            2/2       Running   0          57s
my-cluster-kafka-2                            2/2       Running   0          57s
my-cluster-zookeeper-0                        2/2       Running   0          1m
my-cluster-zookeeper-1                        2/2       Running   0          1m
my-cluster-zookeeper-2                        2/2       Running   0          1m
strimzi-cluster-operator-56d699b5c5-ch9r2     1/1       Running   0          2m
```

Create the topics for the hello strimzi application:
```
$ oc apply -f kafka/kafka-topic1.yaml
$ oc apply -f kafka/kafka-topic2.yaml
```

Confirm on each Kafka broker that the topics were replicated.

```
$ oc exec -it my-cluster-kafka-0 -c kafka -- bin/kafka-topics.sh --zookeeper localhost:2181 --list
my-topic-1
my-topic-2
```
```
$ oc exec -it my-cluster-kafka-1 -c kafka -- bin/kafka-topics.sh --zookeeper localhost:2181 --list
my-topic-1
my-topic-2
```
```
$ oc exec -it my-cluster-kafka-2 -c kafka -- bin/kafka-topics.sh --zookeeper localhost:2181 --list
my-topic-1
my-topic-2
```



#### Kafka Producer

Create a new project for the producer application and change to it:
```
$ oc new-project hello-strimzi-producer
$ oc project hello-strimzi-producer
```

Create new application for the producer:
```
 $ oc new-app openshift/wildfly-101-centos7~https://github.com/lcspangler/hello-strimzi.git --context-dir=/hello-strimzi-producer --name=hello-strimzi-producer
```

Confirm the build completes successfully:
```
$ oc logs -f bc/hello-strimzi-producer
```

Expose the producer application through a route:
```
$ oc expose svc/hello-strimzi-producer
route.route.openshift.io/hello-strimzi-producer exposed
```

Make a configmap for the environment variables:
```
$ oc create configmap hello-strimzi-producer-config --from-file=hello-strimzi-producer/src/main/resources/properties/producer.properties
```
 
Note: Edit bootstrap IP in the configmap as needed based on the my-cluster-kafka-bootstrap service in kafka-cluster project
```
$ oc get services
NAME                                  TYPE           CLUSTER-IP       EXTERNAL-IP                     PORT(S)                      AGE
my-cluster-kafka-0                    LoadBalancer   172.30.117.204   172.29.205.64,172.29.205.64     9094:30011/TCP               55m
my-cluster-kafka-1                    LoadBalancer   172.30.7.146     172.29.222.104,172.29.222.104   9094:30334/TCP               55m
my-cluster-kafka-2                    LoadBalancer   172.30.218.173   172.29.140.253,172.29.140.253   9094:31135/TCP               55m
my-cluster-kafka-bootstrap            ClusterIP      172.30.183.52    <none>                          9091/TCP,9092/TCP            55m
my-cluster-kafka-brokers              ClusterIP      None             <none>                          9091/TCP,9092/TCP            55m
my-cluster-kafka-external-bootstrap   LoadBalancer   172.30.84.134    172.29.241.3,172.29.241.3       9094:32554/TCP               55m
my-cluster-zookeeper-client           ClusterIP      172.30.204.158   <none>                          2181/TCP                     55m
my-cluster-zookeeper-nodes            ClusterIP      None             <none>                          2181/TCP,2888/TCP,3888/TCP   55m
```
 

 
 
Try the application URL:

// create project
// create app
// create config map from yaml
// check bootstrap server
// apply the config map to app as env variables
// build and deploy

#### Kafka Stream


#### Kafka Consumer


#### Kafka Stream Queries



## Automated Setup

<TBA>



## Demo

<TBA>