FROM ubuntu

WORKDIR /translation
COPY ./ /translation

RUN apt-get update && apt-get -y install git maven vim

EXPOSE 8888

CMD ["bash", "start.sh"]
