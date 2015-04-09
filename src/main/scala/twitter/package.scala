package object twitter {

  import scala.util.Failure
  import scala.util.Properties
  import scala.util.Success
  import scala.util.Try
  import twitter4j._
  import twitter4j.conf.Configuration


  lazy val envConfig = new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(Properties.envOrElse("API_KEY", ""))
      .setOAuthConsumerSecret(Properties.envOrElse("API_SECRET", ""))
      .setOAuthAccessToken(Properties.envOrElse("ACCESS_TOKEN", ""))
      .setOAuthAccessTokenSecret(Properties.envOrElse("ACCESS_TOKEN_SECRET", ""))
      .build()

  def tweet(t4j: Twitter)(text: String): Try[Status] =
    Try(t4j.updateStatus(text.take(140)))

  def tweetUrl(status: Status): String =
    "https://twitter.com/" + status.getUser().getScreenName() + "/status/" +
      status.getId()
}
