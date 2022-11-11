FROM ubuntu

WORKDIR /translation
COPY ./ /translation
COPY /root/njhxzhangjh-a65b5a404a03.json /translation/njhxzhangjh-a65b5a404a03.json

RUN apt-get update && apt-get -y install git maven vim

CMD ["bash", "start.sh"]
