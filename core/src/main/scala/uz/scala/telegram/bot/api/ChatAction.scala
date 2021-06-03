package uz.scala.telegram.bot.api

/**
 * ChatAction
 *
 * Type of action to broadcast.
 *
 * Choose one, depending on what the user is about to receive:
 * typing for text messages,
 * upload_photo for photos,
 * record_video or upload_video for videos,
 * record_audio or upload_audio for audio files,
 * upload_document for general files,
 * find_location for location data.
 */
object ChatAction extends Enumeration {
  type ChatAction = Value
  val Typing        : Value = Value("typing")
  val UploadPhoto   : Value = Value("upload_photo")
  val RecordVideo   : Value = Value("record_video")
  val UploadVideo   : Value = Value("upload_video")
  val RecordAudio   : Value = Value("record_audio")
  val UploadAudio   : Value = Value("upload_audio")
  val UploadDocument: Value = Value("upload_document")
  val FindLocation  : Value = Value("find_location")
}
