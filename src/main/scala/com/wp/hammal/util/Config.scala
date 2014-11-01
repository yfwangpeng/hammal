package com.wp.hammal.util

import com.typesafe.config.{ ConfigFactory}

trait Config {

	implicit class WrappedConfig(config: com.typesafe.config.Config) {
		def get(key: String, orElse: =>String) =
			try {
				config.getString(key)
			} catch {
				case ce: ConfigException => orElse
			}
	}

	protected lazy val appConfig = 
		try {
			ConfigFactory.load    
		} catch {
			case ce: ConfigException => throw new ConfigException("Configuration failed to load", ce)
		}

//	private val configRoot = "spider"

	def poolConfig(configRoot: String, name: String="properties") =
		try {
			appConfig.getConfig(s"${configRoot}.${name}")
		} catch {
			case ce: ConfigException => 
				throw new ConfigException(s"Missing configuration of pool $name", ce)
		}
}