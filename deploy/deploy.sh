echo "=> Remove previous container..."
docker rm -f earth-is-round

# build 성공/실패 여부에 따라 분기
if [ $? == 1 ]; then
  echo "=> Build Fail ☠️"
  exit 1
else
  echo "=> Build Success 🌞"
fi

echo "=> Run container..."
docker run -d -p 8080:8080 \
--name earth-is-round \
-e PHASE=prod \
earth-is-round
