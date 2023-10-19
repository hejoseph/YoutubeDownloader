# YouTube Downloader

A simple YouTube video downloader written in Java.

## Description

This Java program allows you to download YouTube videos in MP4 and MP3 formats. It provides options to choose the resolution for MP4 downloads, and you can download individual videos, entire playlists, or a list of videos from a text file.

## Features

- Download individual YouTube videos in MP4 format.
- Download YouTube videos in MP3 format.
- Download entire YouTube playlists.
- Download videos from a list specified in a text file.
- Change configuration settings for the downloader.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

```sh
   git clone https://github.com/hejoseph/YoutubeDownloader.git
   cd YoutubeDownloader
   mvn clean install
   java -jar ./target/YoutubeDownloader.jar
```


## Usage

- Upon running the program, you will be prompted to select from various options to download YouTube videos or change configuration settings. Here are some example outputs:

```java
--------------------------------
Configuration settings
destinationFolderName: C:\workspace\stream\youtube\
downloadedFileName: %(channel)s-%(upload_date)s-%(title)s-%(resolution)s.%(ext)s
--------------------------
YouTube Video Downloader
1. One video MP4 format and choose resolution
2. MP3 format
3. Download full youtube playlist and choose resolution
4. Download list of videos from text file and choose resolution
5. Change Configuration Settings
Enter your choice: 1
------------------------------
Current Configuration Values:
destinationFolderName = C:\workspace\stream\youtube\
downloadedFileName = %(channel)s-%(upload_date)s-%(title)s-%(resolution)s.%(ext)s
Enter YouTube video URL: https://www.youtube.com/watch?v=oLMkUKAiVDY
Enter resolution : 360?480?720?1080?best? ; best for faster download : 360
selecting best resolution for the video (https://www.youtube.com/watch?v=oLMkUKAiVDY), if 360 does not exist for it...
249          webm       audio only tiny   53k , webm_dash container, opus  (48000Hz), 91.15KiB
250          webm       audio only tiny   70k , webm_dash container, opus  (48000Hz), 121.42KiB
140          m4a        audio only tiny  129k , m4a_dash container, mp4a.40.2 (44100Hz), 223.47KiB
251          webm       audio only tiny  139k , webm_dash container, opus  (48000Hz), 239.05KiB
394          mp4        256x144    144p   65k , mp4_dash container, av01.0.00M.08, 30fps, video only, 112.27KiB
160          mp4        256x144    144p   77k , mp4_dash container, avc1.4d400c, 30fps, video only, 131.33KiB
278          webm       256x144    144p   93k , webm_dash container, vp9, 30fps, video only, 159.76KiB
395          mp4        426x240    240p  129k , mp4_dash container, av01.0.00M.08, 30fps, video only, 221.21KiB
133          mp4        426x240    240p  151k , mp4_dash container, avc1.4d4015, 30fps, video only, 258.38KiB
242          webm       426x240    240p  174k , webm_dash container, vp9, 30fps, video only, 297.47KiB
396          mp4        640x360    360p  245k , mp4_dash container, av01.0.01M.08, 30fps, video only, 417.96KiB
134          mp4        640x360    360p  302k , mp4_dash container, avc1.4d401e, 30fps, video only, 515.78KiB
243          webm       640x360    360p  343k , webm_dash container, vp9, 30fps, video only, 585.12KiB
135          mp4        854x480    480p  401k , mp4_dash container, avc1.4d401f, 30fps, video only, 684.25KiB
397          mp4        854x480    480p  420k , mp4_dash container, av01.0.04M.08, 30fps, video only, 717.28KiB
244          webm       854x480    480p  637k , webm_dash container, vp9, 30fps, video only, 1.06MiB
136          mp4        1280x720   720p  607k , mp4_dash container, avc1.4d401f, 30fps, video only, 1.01MiB
398          mp4        1280x720   720p  824k , mp4_dash container, av01.0.05M.08, 30fps, video only, 1.37MiB
247          webm       1280x720   720p 1014k , webm_dash container, vp9, 30fps, video only, 1.69MiB
18           mp4        640x360    360p  432k , avc1.42001E, 30fps, mp4a.40.2 (44100Hz)
22           mp4        1280x720   720p  737k , avc1.64001F, 30fps, mp4a.40.2 (44100Hz) (best)
running cmd : youtube-dl -f mp4 -f 18+140 --no-playlist -o C:\workspace\stream\youtube\%(channel)s-%(upload_date)s-%(title)s-%(resolution)s.%(ext)s https://www.youtube.com/watch?v=oLMkUKAiVDY
[youtube] oLMkUKAiVDY: Downloading webpage
[youtube] Downloading just video oLMkUKAiVDY because of --no-playlist
[download] Destination: C:\workspace\stream\youtube\Skyler-20170430-Jesus Christ Bruce...-640x360.f18.mp4

[download]   0.1% of 736.86KiB at Unknown speed ETA Unknown ETA
[download]   0.4% of 736.86KiB at Unknown speed ETA Unknown ETA
[download]   0.9% of 736.86KiB at  3.42MiB/s ETA 00:00         
[download]   2.0% of 736.86KiB at  7.33MiB/s ETA 00:00         
[download]   4.2% of 736.86KiB at 21.02KiB/s ETA 00:35         
[download]   5.7% of 736.86KiB at 14.76KiB/s ETA 00:49         
[download]   6.8% of 736.86KiB at 17.57KiB/s ETA 00:41         
[download]   8.9% of 736.86KiB at 23.18KiB/s ETA 00:31         
[download]  13.3% of 736.86KiB at 23.35KiB/s ETA 00:31         
[download]  16.5% of 736.86KiB at 25.55KiB/s ETA 00:28         
[download]  22.1% of 736.86KiB at 26.80KiB/s ETA 00:27         
[download]  26.4% of 736.86KiB at 27.07KiB/s ETA 00:27         
[download]  30.3% of 736.86KiB at 28.54KiB/s ETA 00:25         
[download]  36.4% of 736.86KiB at 30.75KiB/s ETA 00:23         
[download]  43.1% of 736.86KiB at 30.21KiB/s ETA 00:24         
[download]  46.9% of 736.86KiB at 29.21KiB/s ETA 00:25         
[download]  49.8% of 736.86KiB at 26.81KiB/s ETA 00:27         
[download]  51.3% of 736.86KiB at 27.64KiB/s ETA 00:26         
[download]  54.4% of 736.86KiB at 26.98KiB/s ETA 00:27         
[download]  57.0% of 736.86KiB at 27.65KiB/s ETA 00:26         
[download]  62.3% of 736.86KiB at 28.63KiB/s ETA 00:25         
[download]  68.6% of 736.86KiB at 28.75KiB/s ETA 00:25         
[download]  72.7% of 736.86KiB at 29.53KiB/s ETA 00:24         
[download]  80.1% of 736.86KiB at 29.88KiB/s ETA 00:24         
[download]  84.7% of 736.86KiB at 30.34KiB/s ETA 00:24         
[download]  90.3% of 736.86KiB at 30.79KiB/s ETA 00:23         
[download]  95.6% of 736.86KiB at 31.22KiB/s ETA 00:23         
[download] 100.0% of 736.86KiB at 32.11KiB/s ETA 00:22         
[download] 100% of 736.86KiB in 00:25                          
[dashsegments] Total fragments: 1
[download] Destination: C:\workspace\stream\youtube\Skyler-20170430-Jesus Christ Bruce...-NA.f140.m4a

[download]   0.4% of ~223.47KiB at  260.06B/s ETA 14:35
[download]   1.2% of ~223.47KiB at  670.73B/s ETA 05:37
[download]   2.6% of ~223.47KiB at  1.46KiB/s ETA 02:29
[download]   5.4% of ~223.47KiB at  3.06KiB/s ETA 01:09
[download]  11.1% of ~223.47KiB at  5.93KiB/s ETA 00:33
[download]  22.4% of ~223.47KiB at  9.28KiB/s ETA 00:18
[download]  31.6% of ~223.47KiB at 13.11KiB/s ETA 00:11
[download]  50.1% of ~223.47KiB at 18.73KiB/s ETA 00:05
[download]  81.4% of ~223.47KiB at 25.08KiB/s ETA 00:01
[download] 100.0% of ~223.47KiB at 29.57KiB/s ETA 00:00
[download] 100.0% of ~223.47KiB at 29.55KiB/s ETA 00:00
[download] 100% of 223.47KiB in 00:07                  
[ffmpeg] Merging formats into "C:\workspace\stream\youtube\Skyler-20170430-Jesus Christ Bruce...-640x360.mp4"
Deleting original file C:\workspace\stream\youtube\Skyler-20170430-Jesus Christ Bruce...-640x360.f18.mp4 (pass -k to keep)
Deleting original file C:\workspace\stream\youtube\Skyler-20170430-Jesus Christ Bruce...-NA.f140.m4a (pass -k to keep)
Video downloaded successfully at :C:\workspace\stream\youtube\Skyler-20170430-Jesus Christ Bruce...-640x360.f18.mp4
Execution time: 00:00:56:084
--------------------------
YouTube Video Downloader
1. One video MP4 format and choose resolution
2. MP3 format
3. Download full youtube playlist and choose resolution
4. Download list of videos from text file and choose resolution
5. Change Configuration Settings
Enter your choice: 