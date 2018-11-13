# hello-strimzi

A Strimzi hello world example demonstrating Kafka:
- Producer
- Stream
- Consumer
- Stream Queries

Uses s2i with the Wildfly - CentOS docker image on Minishift.
The official Jboss EAP 7 image and OpenShift can be substituted. 

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

Install most recent `oc` binary:
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




#### Kafka Producer


#### Kafka Stream


#### Kafka Consumer


#### Kafka Stream Queries



## Automated Setup

<TBA>



## Demo

<TBA>