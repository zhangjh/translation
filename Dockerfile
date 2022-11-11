FROM ubuntu

WORKDIR /translation
COPY ./ /translation

RUN apt-get -y install git maven

EXPOSE 8888

CMD ["bash", "start.sh"]
