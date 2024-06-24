import cv2
import numpy as np
import sys
import json

srcImg = ["notAllMax.png", "4on5max.png", "inBattle.png"]
srcTmpl = ["victoryLogoStars.png", "lvlMax.png"]
methods = [#'cv2.TM_CCOEFF', 'cv2.TM_CCOEFF_NORMED', 'cv2.TM_CCORR', 'cv2.TM_CCORR_NORMED',
    'cv2.TM_SQDIFF', 'cv2.TM_SQDIFF_NORMED']
thresholds = [.95, .96, .97, .98, .985, .99, 1.]

if __name__ == '__main__':
    if len(sys.argv) != 5:
        print("ERROR")
        exit(1)
    method = eval(sys.argv[1])
    srcImg = sys.argv[2]
    srcTempl = sys.argv[3]
    threshold = float(sys.argv[4])
        
    src = cv2.imread(srcImg, cv2.IMREAD_UNCHANGED)
    img = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)

    tem = cv2.imread(srcTempl, cv2.IMREAD_UNCHANGED)

    h, w = tem.shape[:2]
    tem_gray = cv2.cvtColor(tem,cv2.COLOR_BGR2GRAY)
    mask = np.invert(cv2.threshold(tem_gray, 254, 255, cv2.THRESH_BINARY)[1])

    res = cv2.matchTemplate(img, tem_gray, method=cv2.TM_SQDIFF_NORMED, mask = mask)
    #Image.fromarray(res).show()
    loc = None
    if method in [cv2.TM_SQDIFF, cv2.TM_SQDIFF_NORMED]:
        loc = np.where( res <= (1-threshold))
    else:
        loc = np.where( res >= threshold)

    retour = []
    for pt in zip(*loc[::-1]):
        retour.append([int(pt[0]), int(pt[1])])
    print(json.dumps(retour))
    exit(0)