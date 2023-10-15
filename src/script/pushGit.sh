#bin/bash

git add *
git commit -m "commit by pushGit.sh"
git push origin main
cd data
git add *
git commit -m "commit by pushGit.sh"
git push origin main
cd ../
