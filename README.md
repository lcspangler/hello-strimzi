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
$ oc apply -f https://github.com/strimzi/strimzi-kafka-operator/releases/download/0.8.2/strimzi-cluster-operator-0.8.2.yaml -n kafka-cluster
```

Provision an ephemeral Kafka cluster:
```
$ oc apply -f https://raw.githubusercontent.com/strimzi/strimzi-kafka-operator/0.8.2/examples/kafka/kafka-ephemeral.yaml -n kafka-cluster
```

Watch the deployment until all Kafka pods are created and running:
```
$ oc get pods -n kafka-cluster -w
NAME                                          READY     STATUS    RESTARTS   AGE
my-cluster-entity-operator-5d7cd7774c-gbf2w   2/3       Running   849        7d
my-cluster-kafka-0                            2/2       Running   125        7d
my-cluster-kafka-1                            2/2       Running   123        7d
my-cluster-kafka-2                            2/2       Running   129        7d
my-cluster-zookeeper-1                        2/2       Running   86         7d
my-cluster-zookeeper-2                        2/2       Running   88         7d
strimzi-cluster-operator-56d699b5c5-q65v2     1/1       Running   98         7d
```

Create the topics for the hello strimzi application:
```
$ oc apply -f kafka/kafka-topic1.yaml
$ oc apply -f kafka/kafka-topic2.yaml
```

Confirm on each Kafka broker that the topics were replicated.


#### Kafka Producer



#### Kafka Stream


#### Kafka Consumer


#### Kafka Stream Queries



## Automated Setup

<TBA>



## Demo

<TBA>