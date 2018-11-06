\ LP format - for model browsing. Use MPS format to capture full model detail.
Maximize
  0.2003 mbps + 0.0194 width - 0.0404 height + 0.0063 fps
   + 3.401197381662155 Constant
Subject To
 widthRestriction: - width + 320 y01 + 640 y11 + 720 y21 + 800 y31
   + 1024 y41 + 1280 y51 + 1366 y61 + 1440 y71 + 1600 y81 + 1680 y91
   + 1920 y101 + 2560 y111 + 3840 y121 = 0
 c0: y01 + y11 + y21 + y31 + y41 + y51 + y61 + y71 + y81 + y91 + y101
   + y111 + y121 = 1
 heightRestriction: - height + 240 z01 + 480 z11 + 600 z21 + 730 z31
   + 768 z41 + 900 z51 + 1050 z61 + 1080 z71 + 1440 z81 + 2160 z91 = 0
 c1: z01 + z11 + z21 + z31 + z41 + z51 + z61 + z71 + z81 + z91 = 1
 mbpsRestriction: - mbps + 4 w01 + 12 w11 + 12 w21 + 16 w31 + 20 w41 = 0
 c2: w01 + w11 + w21 + w31 + w41 = 1
 fpsRestriction: - fps + 15 v01 + 24 v11 + 30 v21 + 45 v31 + 60 v41 = 0
 c3: v01 + v11 + v21 + v31 + v41 = 1
 c4: 0.2003 mbps + 0.0194 width - 0.0404 height + 0.0063 fps
   <= -2.70805020110221
 c5: 0.5 width - height <= 0
Bounds
 Constant = 1
Binaries
 y01 y11 y21 y31 y41 y51 y61 y71 y81 y91 y101 y111 y121 z01 z11 z21 z31 z41
 z51 z61 z71 z81 z91 w01 w11 w21 w31 w41 v01 v11 v21 v31 v41
Generals
 mbps width height fps
End
