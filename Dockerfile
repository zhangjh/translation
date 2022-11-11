FROM ubuntu

WORKDIR /translation
COPY ./ /translation

EXPOSE 8888

CMD ["bash", "start.sh"]
