package com.ruchij.exceptions

import reactivemongo.api.commands.WriteError

case class MongoInsertionException(writeErrors: List[WriteError]) extends Exception
