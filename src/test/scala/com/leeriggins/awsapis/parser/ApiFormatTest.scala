package com.leeriggins.awsapis.parser

import org.scalatest._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization._
import com.leeriggins.awsapis.models._
import com.leeriggins.awsapis.parser.Apis._

class ApiFormatTest extends WordSpec with Matchers {

  val apis = IndexedSeq(
    "autoscaling" -> "2011-01-01",
    "cloudformation" -> "2010-05-15",
    "cloudfront" -> "2014-11-06",
    "cloudhsm" -> "2014-05-30",
    "cloudsearch" -> "2013-01-01",
    "cloudsearchdomain" -> "2013-01-01",
    "cloudtrail" -> "2013-11-01",
    "codedeploy" -> "2014-10-06",
    "cognito-identity" -> "2014-06-30",
    "cognito-sync" -> "2014-06-30",
    "config" -> "2014-11-12",
    "datapipeline" -> "2012-10-29",
    "directconnect" -> "2012-10-25",
    "dynamodb" -> "2012-08-10",
    "ec2" -> "2014-10-01",
    "ecs" -> "2014-11-13",
    "elasticache" -> "2015-02-02",
    "elasticbeanstalk" -> "2010-12-01",
    "elasticloadbalancing" -> "2012-06-01",
    "elasticmapreduce" -> "2009-03-31",
    "elastictranscoder" -> "2012-09-25",
    "email" -> "2010-12-01",
    "glacier" -> "2012-06-01",
    "iam" -> "2010-05-08",
    "importexport" -> "2010-06-01",
    "kinesis" -> "2013-12-02",
    "kms" -> "2014-11-01",
    "lambda" -> "2015-03-31",
    "logs" -> "2014-03-28",
    "machinelearning" -> "2014-12-12",
    "mobileanalytics" -> "2014-06-05",
    "monitoring" -> "2010-08-01",
    "opsworks" -> "2013-02-18",
    "rds" -> "2014-10-31",
    "redshift" -> "2012-12-01",
    "route53" -> "2013-04-01",
    "route53domains" -> "2014-05-15",
    "s3" -> "2006-03-01",
    "sdb" -> "2009-04-15",
    "sns" -> "2010-03-31",
    "sqs" -> "2012-11-05",
    "ssm" -> "2014-11-06",
    "storagegateway" -> "2013-06-30",
    "sts" -> "2011-06-15",
    "support" -> "2013-04-15",
    "swf" -> "2012-01-25",
    "workspaces" -> "2015-04-08")

  implicit val formats = DefaultFormats + AwsApiTypeParser.Format + InputParser.Format + OutputParser.Format

  "The Api format" should {
    apis.foreach {
      case (apiName, apiVersion) =>
        s"deserialize then serialize ${apiName} version ${apiVersion} without changes" in {
          val text = Apis.json(apiName, apiVersion, ApiType.min)
          val parsedText = parse(text)

          val api = parsedText.extract[Api]

          val reserialized = parse(write(api))

          val Diff(changed, added, removed) = parsedText diff reserialized
          changed should be(JNothing)
          added should be(JNothing)
          removed should be(JNothing)
        }
    }
  }

}