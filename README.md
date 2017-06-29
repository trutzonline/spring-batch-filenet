## Deployment

```
GPG_TTY=$(tty)
export GPG_TTY
mvn clean deploy
```

Login to https://oss.sonatype.org and close and release staging repository.