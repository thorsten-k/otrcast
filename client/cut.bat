echo Processing: Falling_Skies_11.12.12_20-15_pro7_115_TVOON_DE.mpg.HQ.avi

rm -rf tmp/*
/Users/thorsten/Dropbox/Developing/OTR/Tools/MP4Box.exe -aviraw video /Users/thorsten/Dropbox/Developing/OTR/HQ.avi/Falling_Skies_11.12.12_20-15_pro7_115_TVOON_DE.mpg.HQ.avi -out tmp/raw.h264
/Users/thorsten/Dropbox/Developing/OTR/Tools/MP4Box.exe -aviraw audio /Users/thorsten/Dropbox/Developing/OTR/HQ.avi/Falling_Skies_11.12.12_20-15_pro7_115_TVOON_DE.mpg.HQ.avi -out tmp/raw.mp3
/Users/thorsten/Dropbox/Developing/OTR/Tools/lame.exe --decode tmp/raw_audio.mp3 - | /Users/thorsten/Dropbox/Developing/OTR/Tools/faac.exe --mpeg-vers 4 -b 192 -o tmp/aac.aac -
/Users/thorsten/Dropbox/Developing/OTR/Tools/MP4Box.exe  -add tmp/raw_video.h264 -add tmp/aac.aac tmp/mp4.mp4

/Users/thorsten/Dropbox/Developing/OTR/Tools/ffmpeg.exe -ss 321.12 -t 1065.12 -i tmp/mp4.mp4 -vcodec copy -acodec copy tmp/1-1.mp4
/Users/thorsten/Dropbox/Developing/OTR/Tools/ffmpeg.exe -ss 1852.40 -t 775.92 -i tmp/mp4.mp4 -vcodec copy -acodec copy tmp/1-2.mp4
/Users/thorsten/Dropbox/Developing/OTR/Tools/ffmpeg.exe -ss 3151.72 -t 625.29 -i tmp/mp4.mp4 -vcodec copy -acodec copy tmp/1-3.mp4

/Users/thorsten/Dropbox/Developing/OTR/Tools/MP4Box.exe tmp/1-1.mp4 -cat tmp/1-2.mp4 -cat tmp/1-3.mp4 -out "/Users/thorsten/Dropbox/Developing/OTR/MP4/Falling Skies-S1E2-Das Arsenal.mp4"


echo No Cutlist available for: Hawaii_Five_0_11.08.25_21-15_sat1_60_TVOON_DE.mpg.HQ.avi

