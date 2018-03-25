package com.ruchij.models

trait State
{
  def label: String
}

object State
{
  case object Victoria extends State {
    override def label: String = "VIC"
  }

  case object NewSouthWales extends State {
    override def label: String = "NSW"
  }
}
