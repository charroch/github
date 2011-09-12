package com.novoda

import dispatch._
import net.liftweb.json.JsonAST._
import dispatch.liftjson.Js._
import java.io.File
import scopt.OptionParser
import com.novoda.App.GitHub

class App extends xsbti.AppMain {
  def run(config: xsbti.AppConfiguration) = {
    Exit(App.run(config.arguments))
  }
}

object App {
  def run(args: Array[String]): Int = {
    if (parser.parse(args)) {
      new GitHub(config).download
    }
    0
  }

  class Config {
    var organization: String = "";
    var username: String = "";
    var password: String = "";
    var outputDir: String = ".";
  }

  val config: Config = new Config

  val parser = new OptionParser("github") {

    arg("<org>", "<org> is the organization you want to backup", {
      v: String => config.organization = v
    })

    arg("<username>", "<username> to use for connecting to github", {
      v: String => config.username = v
    })

    arg("<password>", "<password> attached to the username", {
      v: String => config.password = v
    })

    opt("o", "output", "<file>", "output is a string property", {
      v: String => config.outputDir = v
    })
  }

  def main(args: Array[String]) {
    System.exit(run(args))
  }


  class GitHub(conf: Config) {

    def download {
      fetch.foreach((nv) => gitClone(nv._1, nv._2))
    }

    def fetch: List[(String, String)] = {
      val http = new Http()
      val u = url("https://api.github.com/orgs/%s/repos" format (conf.organization)).as_!(conf.username, conf.password)
      val r: List[(String, String)] = http(u ># {
        json =>
          for {
            JObject(child) <- json
            JField("name", JString(name)) <- child
            JField("clone_url", JString(url)) <- child
          } yield (name, url)
      })
      http.shutdown()
      r
    }

    def gitClone(gitname: String, gitUrl: String) {
      import org.eclipse.jgit.api.CloneCommand
      import org.eclipse.jgit.lib.TextProgressMonitor
      import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
      new CloneCommand()
        .setURI(gitUrl)
        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(conf.username, conf.password))
        .setCloneAllBranches(true)
        .setDirectory(new File(conf.outputDir, gitname))
        .setProgressMonitor(new TextProgressMonitor())
        .call()
    }
  }

}

case class Exit(val code: Int) extends xsbti.Exit



