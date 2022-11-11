FROM ubuntu

WORKDIR /translation
COPY ./ /translation

RUN apt-get update && apt-get -y install git maven vim

CMD ["bash", "start.sh"]
