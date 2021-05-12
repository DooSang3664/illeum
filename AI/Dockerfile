FROM getcurrentthread/python-fastapi-image:0.0.2

WORKDIR /app

COPY requirements-linux.txt /app/

# RUN apk --update add tzdata && \
#    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
#    apk del tzdata && \
#    rm -rf /var/cache/apk/*

RUN python -m pip install --upgrade pip

RUN pip install cmake

RUN pip install -r /app/requirements-linux.txt

COPY . /app

RUN chmod +x /app/run.sh

CMD ["/app/run.sh"]
