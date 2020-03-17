FROM ubuntu:latest

# Setup python and java and base system
ENV DEBIAN_FRONTEND noninteractive
ENV LANG=en_US.UTF-8

RUN apt-get update && \
  apt-get upgrade -y && \
  apt-get install -q -y openjdk-8-jdk python3-pip libsnappy-dev language-pack-en supervisor wget

RUN pip3 install --upgrade pip requests


RUN wget https://github.com/mozilla/DeepSpeech/releases/download/v0.6.1/deepspeech-0.6.1-models.tar.gz && \
   tar -zxvf deepspeech-0.6.1-models.tar.gz && \
   rm deepspeech-0.6.1-models.tar.gz 

RUN pip install deepspeech

RUN apt-get update && apt-get install -y maven


COPY . .

RUN mvn clean package

RUN mkdir /tmp/module2_files

CMD ["java", "-cp", "target/classes", "application.App"]
