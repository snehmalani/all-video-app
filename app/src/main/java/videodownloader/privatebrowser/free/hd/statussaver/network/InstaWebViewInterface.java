package videodownloader.privatebrowser.free.hd.statussaver.network;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import androidx.annotation.Keep;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.MainActivityVideos;
import org.apache.commons.lang3.StringUtils;

@Keep
public final class InstaWebViewInterface {
    public static final String INSTA_ACCOUNT_BATCH_POSTS = "_aa6a _aatb _aatd _aatf _aath";
    public static final String INSTA_ACCOUNT_POSTS = "_aa08 _aatb _aatc _aatd _aatf _aath";
    public static final String INSTA_BATCH_POSTS = "_abj9 _ab6k _aatb _aatc _aatd _aatf _aath";
    public static final String INSTA_FEED = "_abj9 _ab6k _aatb _aatc _aatd _aatf";
    public static final String INSTA_PUBLIC_POSTS = "_aatb _aatc _aatd _aatf";
    public static final String INSTA_SEARCH_POST = "_aa08 _aatb _aatc _aatd _aatf";
    public static final String INSTA_SINGLE_POST = "_aa6a _aatb _aatd _aatf";
    private final MainActivityVideos activityVideos;
    private long lastTimeClicked;
    private final WebView webView;


    public InstaWebViewInterface(MainActivityVideos activityVideos, WebView webView2) {
        this.webView = webView2;
        this.activityVideos = activityVideos;
    }

    private void addButton(final String str, final String[] strArr, final String str2) {
        this.webView.post(() -> {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                WebView webView = this.webView;
                webView.loadUrl("javascript:(function() { var parents=document.getElementsByClassName('" + str + "');var parent=parents[" + i + "];var items=parent.getElementsByClassName('" + str2 + "'); \nvar checkList=parent.getElementsByClassName('_acaz'); \n\tif(checkList.length<1){\nfor (var i = 0; i < items.length; i++) { \n\tif(items[i].getElementsByClassName('newspan').length<1){\n\t\tvar button=document.createElement('button');\n    \tbutton.style.width = '28px';\n    \tbutton.style.height = '28px';\n    \tbutton.style.marginTop = '6px';\n    \tbutton.style.outline = 'none';\n    \tbutton.style.border = 'none';\n    \tbutton.style.backgroundSize = 'cover';\n    \tbutton.style.backgroundColor = '#00000000';\n    \tbutton.className='button_'+" + i + "+'_'+i;\n    \t button.style.backgroundImage= 'url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAH0CAMAAAD8CC+4AAACQ1BMVEUAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA0HgAkFQBpPgD0oAD9owCeXgD+rAD+rAC2aAD6pAD1jQD9qgBOLgD9lwD8jwDhggD9qwD7qQD7pQDfkgD+rQD9pwD6pAD8mADQegD6jgD9qwD3owD+jwD8ngDwiQC/bgD7owD8kgDnhADUeAD+mgDuiQD+qwD+rADZfQDIdgD+qAD7lwD+pQD9qAD+qgD+rQD9nADXhQCGTwB+SQBgOQD6owDUgwDqlQD+pwDwlwDjhADbfwCuZQClYQD+qQB1SQCYYQDzoAC2dAD9qgD3mQD6pQD4jQD8qQDmkADllwDZiwD+qwC9dACSVwCIUgC+dwD+nQDzjwDDegDciQDPggDtmgD9pgD9nAD7owDdhwCTXQA+JgDfjwDtngDzjgC7cwDjkgDumwBnPwCSWwD/////mQD/kgD+pAD/lAD+qAD+pwD+qwD/nAD+oQD/lgD+nwD/ngD+qgD+rQD/3qL/68j/6sj//Pf+tCv+rR//qh7//vv/+u//sSz/riz/piL/qy3/1o/+rx/+sh//+/P/pAv/8Nf/36L/2qP/2Jv/yXL/9ub/rTf/2aD/nAj/4LL/0ID/58L/9OL/8t3/5r7/wmL/9+r/47j/2Jb/u1X/phj+qxb/7M7/zXn/nw7/04j/qSn/nxb/3Kf/uUX/skD+uTn/pxL/3qv/ox7/v13/xWr+qA3+wlBSWku1AAAAeXRSTlMATQRJBhkOIjYoDEQIQC8VPRIeMjtZUmsP+YXz7ZhO5Wth9/fDl6srCPjz2tSv87wc+/LgoXjz0Lf62J/OvKpi7eqNOdabiXl0Zye4nFbhysCSjIQnFhWYe29B7uLQv7myiH5pT72tnnVDM8mwpZpkRSLLw29f2VI/rksmSgAAI31JREFUeNrs282Ko0AUhuG6DEURN/4PGAZcKYia3gRcCUFCFm5609J3EGYkiz63PcdFxumZDJ1Om0St77mFl2NVaSkAAAAAAAAAAAAAAAAAPqZsCiONKituwjZZ73X21tPZfh+0YRNbVZQaxUYRMG/bojRzu91r3cnbeT8G2r61c7MstgLmRSnSyk6G1pc3H/iJXaUFJn8GlMK0wqwbXNd8kIWWifSTpewie931xmo+COxoh/IT46Tx0Hvc5oMgTh0Bk1BEjdax2zZnP5nfRIWAhyrqsA9+n+Ynfli/CHiIVWrrHbtj80FmpysB91VUScce0HwQVHjS341rxDziD2/ey+LSFXBrSmnzKj6R5j3fLnGYuyW3bDzqJtW8pzaY91sxYo1ogs17/qshYGxOrhNNtnkvy/HqZkyKmRBNvDk7tCaW95EUtkezaM7UVxzjxhjyNdFsmvcCjPvXOJZGM2vOfAur+9WMkGiGzXshNvPXcM01zbY5C0yc3T9JqXWadXOW1VjcP2GVazT75szP8SnuQivLo0U0Z6qF7BcmX0xzZL80+aKaI/tHtrlHi2vO1Bz/TvyHUmu0yObMr7CTP8fUabHNWWYK+IuxpkU3ZwHe0r3zEtLim7NnXJ3+bWuRFM3ZE3Z0p8VcmuaHw3cs7WyXkETNWbATktvGJFlzFsv9jE91CZvz8S0V0tqEJGXz4/H4vBFyijxpmx+PaiQk5CQkcXPWyneRrvYkb87DXgup8JhL31y2YTc9NJdsZV+FhOYnz3JcsDB0NP9DJsG3N9ciNH/PWvoFeSdB8398W/Z+LtXQ/Ax1wa9l3ZjQ/LynpT7if7F3P6tpRFEcxy/zFJYUCYV0DBUiggtpICSkmwFX2SShi2zcpQ8ws2g54PUPCpmNDkkg2Yi1NqChiyBZ1Efr3XWTxJmDzPzOcL+v8EEd75x7r7dvzV+tnM/F+OKWNX+jah6f4k861vztTlTOKlxY87Vd5ms23ju25utrfcrTD3txy5rHMG+1cvTDvt2x5rHMTWcqH+1Y87jmpi8qBxX2rHl8c1NT/uNc5diaJzE3laW/bXW/WvNk5qZD2S9gzt9b86TmpqrkTTCNXWue3Nz0rqGktt2x5ixzk9R9jqfWnGtukjkyeWTN+eamIyWvHWvONJe7TPPZmjPN//dNyeqjNY9nnh/1kjXfgLnpUs7oXOnCmm/A3KTlqNvP+abMtRh1UPNoNZz+nIUvNpv0Hn4NAM2NupIQ5nP7YLyYhH3/1frhZPFniWeutYSnOUjz9l1v5q8tXDzO4cy1xv+/DrkOFw3v/ViF3SWcudboa3OQ6+1R14/ddAlnrjX2Ojzke7W5MU+gHsGZa408LtlANG8P+36Snq/hzHUL9/36OeTMxNONn6j7OzhzreuoszTuFqJ5e3qVDP3qZgRnrnUVc26uAjkD2V6FfsL6j3/hzLU+RJyRLWDOOs+f+76f+KOOZ651GXAeHnRPw2DmJ+/pGs9cU1OhBbp36fvKZzSeA5oToS3Noe5RnI856LcjRHMirL/rRVDzH6MHDvrvAaQ5EdJOZg92/3nU5aD3BpjmVMU5taCAe85EdMtDxzQnwnmEBz5PhosOak6EMlOBfG4UFx3VnAjjDKoGsDkbHdacCOHdiwd9JiAXHdcc4WGugH32Jxcd15yonPmALPgZv1x0YPPsV+YOsM3Z6MjmRAcqy1z089u56NDm9MFV2VWCv6eBiw5tnu3POv59LFx0bPMsf9aL8OZsdHDz7F69VATctcVFRzenakVlkSPhTj0uOro5UdNR6efUBJiz0eHNic4yUHdF3JfKRcc3p7qrUk/GvchcdHxzorJKOedUhDkbXYA5BTVHpZor5M57LroE8yDtL/h9GeZsdAnmQVBWKebUhJiz0UWYB2l+wTverhBzNroM86Dupabu7EkxZ6PLMP/HzR2ztBWGURyHw0uHcD+AkBAkoCgNKAUHUTCI2dzaK41TFwVDHRxcDkKHO3fo0s9b08U5J/cN/PMZfvwPz/TYD9tC1xXGPEaHmNtX21HX3QhjHqNTzD2724q6LjjmMTrF3J5vA133IPMYHWNu39dXly5B5jE6x9zTUl1d+yTzGJ1jbi9qo+tuRDKP0UHm/lL5lpMuUOYxOsjcnququpYs8xidZG4va6JLJyzzGB1l7oeaqWsMM4/RUeb2uB66jm5g5jE6y9zTo1rq0j7NPEZnmdsLVVLX3ohmHqPDzD3bq4MufcWZx9+lYOb2WZ3UdTrEmf/6G6LTzD05rYEuHfDM31J0mrl9K1UwfznmmcfoOHOfv0gVQgeax+g4c3suVQgdaB6j88wrpC4dEM1jdJ75R+p9hk40j9GB5r2nrnKANI/Rgeb2vKjn0JHmMTrRvOfUVa6Z5jE60dy+7TF1qR0yzWN0pLknrdRf6N+h5jE60tw+6y116WgENY/RmeaeHUl9hb5PNY/Rmeb2oqfUpXJDNY/RoeaeFqmf0MdY8xgdam6Pi/oJ/QRrHqNTzf3QS+oqS655jE41t5dFfYR+wTWP0bHmnveQuko75JrH6FhzT9qizUPfB5vH6Fhze7Fx6irNDdg8Rueae9oUbRr6Pdk8Ruea209F2hD9kGweo4PN/VikzczbY7J5jA4293lbpI3QF2jzGB1s/nHKpebNJdo8Rf9DNv9/ymkD9CXbPEYnm9vLTdBLc802j9HR5r5tivLQByO2eYyONvdsUKQ49DHcPEZHm9vjNHW9oz/DzWN0trkf01NOKu0Qbh6js809aVP00rzSzWN0trn92hSFZ9wPunmMDjfvfg6KFIXeHtPNY3S4eTdpk9S1Wne8eYwON++61b4rWfdDvHmMTjfP9n217kO8eYxON4/2fRX6K988Rqebv+/7KvX10Z/55jE63rx7DNBL82nEN3/7HaLjzbvZp6Zo7dCfdsA8Rsebd93TuqmrNIPrHTCP0fnm3e2gKVp33T/vgHmMzjfvpmvuu1QG33bBPEbnm//jzo5V2wiiKAyDYWwiDMNuq4DBhSCSwJVVJxC2UrlNwKRaxIoQFZECbm6TSZUueeLIwnAbz+xwyv8ZPjj3nJmUes/32nT/QjCX0QHmafR8r033BcFcRgeYp53ne2W6X78nmMvoAPPzaPN8r0v3PcJcRgeYp7T3fK9L90eEuYxOME+d53tduj8gzGV0gnk6er5XoN/O4hxhLqMTzNN9nN06+nS67xnmMjrBPKW953tNuj8yzGV0hHnqPN+rTjrDXEZHmPtRrxpscc4wl9ER5mkTfbRVnHSIuYyOMPejXpXuI8RcRmeYp9HzfXKwNd8h5jI6wzztGh9tU+nerCDmMjrDPK0bz/epdO8p5jI6wzyl3vN9Cn2gmP9S0SHmaXD0icHWdBRzGR1inrrGR1v5pMcFxVxGh5inXfSjXk73+JliLqNDzNM6er5P9DiMuYj+5y/E3Jvc5EkfMOYyOsU8DX7Uyyt9xJjL6BRzGyuW+uWkbzHmMjrF3LaXo17R4x4w5jI6xdyO3uSKD+/hE8ZcRqeY28fgz++lk95zzGV0irlZ70e9gB4HjrmMjjG3ITp6oceNHHMZHWNuoze5Qo8LW465jI4xt0PwJlfocQuOuYyOMbedN7lCjwsrjrmMjjG3dfAml+9xYc4xl9Ex5rYJ3uTyPa4HmcvoGHOz3ptc/j1uAJnL6BxzG/xNLofehJ8gcxmdY26n0Dh65l81dCBzGZ1jbl3w39Vsed+CzGV0jrkdvL7ny/sCZC6jc8xt5/U9W97DN5C5iv6PY27r4PU9V97DE8hcRueY213w+p5BD+0HkLmMzjG3+zY4eublvSeZy+gcc7PeX99z5f2ZZC6jg8zt2et7rrwPJHMZHWRuQ4hT6CeSuYwOMreTo+cW20gyl9FB5jb6Zsstto5kLqODzK3zzZZbbAeSuYwOMrdDG4roTWi3JHMZHWRu29b/2d7+Y2sXJHMZHWRuu9b/2d6e6e1XkrmMDjK34xn9uoh+84NkLqODzO14U0aPZ3SSuYwOMn9BjxPoK5K5jA4yt3URfXZBJ5nL6CBzu3tBnxXRn0jmv1V0kHkJ/eoVfUkyl9FB5rZ5Rb/KPsjdLEnmMjrI/Izehil0krmMDjKfQm9C+25JMpfRQeb/ubmb1SaiKIDjIgpT0ajEjStBXfmxEhQEF266EnwARQlduDNBRTleP1B0UaeaAR3JDJpoHJMmGtM0tWmjbX00k1o9g7Xnhptzz8T8n6Dw62TOuZPM/dMTeA67CX33OvqDcTI3Rh8j8/v319F3/xN9xy/0sTI3Rh8n8w30HQT6WJkbo4+TuUL0LY7eJ8bK3Bh9nMzVRP/wnUR/ME7mxujjZD4A+rFxMjdGHyfz0zT63j76aJg3Z5Y7nc7yzJqBORO6ufn8wlKn17uF8giYr6Pv1aCPgHlzueZ/mgvDcHox+jETmJvzo+vNyy870bdn1UolfLZYXHqZuPlA6Imbry1HIWCfas8DA3N76DT5l8aHPPzJe9P5mqD5oOiXEzdvlf4ymO0YmFtDp/2WfpFjhfcLyZqrQ3r04wmbv/7gwV+51aKJOT+63rwWupv++rCTlPng6MmaL0+7sLmKHxiZ86PTfq0q/KNwJUlzdUKPfiNR8840/DPXXzMwZ0enzR8V6wAadXlzdXYA9ETNPwJspf5icHPsBS86vaih+Wb1srQ5dlOPfiXJz/aPhITf1JkzohuZF2DLqqguba6OGqALms8BUb7dpc050U3MPSCqNsqy5thVPfqFxMxnpoHM/XyPMreOfpesZ05WaciaYyf16NcTMw/zQOf5hLlddL15BejyqC5rrq7r0SdH9DrvV/cDwtwiut68CtpQXdRcTerRM0nt5zBABX+NMLeGrjcvARlubvLmKqNHvzV6c3sst90kzO2h0zMcmmvV5c3VLT16NhHzWRg0VEdz6+i0+aoHunBfFzdXWT36nZH9bP+t3iXMudDZzXFzkzZXd/TozmjOcJjbDihzfnTavIzncIOpS5srR4+ePiZuPkfvavTm9sQOuvmuRpevNoTND6dp9P199OMjfZ33q7cJc3Z02nwVd7WBr3VRc3Uorf9iZOr2CN/PN/L8AM2toPNf51jYuCtors6mBkC/Inudz4JBbtRFc6vo9P18tQ4GzXUEzdVRRN/yFy6pC5LmTQKFVm+iuUV0em4nzMlmX8mZq5Mp/c+aUpOC5kELTGt3BzB/aorOsKtRfS6LmatJCn37BvotCXO8oZtWiAK9+TDo+s/2EpgWNsTM1a0NdOr36amsdXPMz4Nxnq81N0e3MsNh+UUxc5VdR6ffRJHKyZk//whDVI905uboDLsa2dw7KXOVS+lfP5Jyjlk2x2oVGEq9HdDm5ugMz1LJvEjK/IijQ9+7s4d+27I59taFoXKjgDQ3R2fY1eg+3JUxV2d76Fu/8H0DfSJ9Rco8mAXMdHOjzM3RdXN7CYYsfCljro6mJzbQt1Ho1+2aY6/nYOiiLmFujq4xL8CwVZdkzNV1Ch2fuGTsmmMzIQxdoRUQ5qboryjzcq0EQ1dpyJirDD5vodCzds2xZQZ08NqEuRX01QpwoMuYqyyFjofvOX5z/isdK7U2mVtFr4XAgi5jrnJ49L4leu90xjlv1RxrTgOLevvxJnN76KshcFR9J2N+yemdzZDou36h37BrjuFvuRk3t6dW0WslYOnZVxFzdcaJHb0T57DOBbvmWOQBj3qri+Y20ee5zN33MubqpBM7haWO5DJ2zbFOCExFXTTnR0dzD3iq1GTMVSZ+IEccyaWzds2x4BMw5bYCNOdHx12NqdnvMuZqKo0HcuTpTM6yOVasA1NehOa20GtVYCrfFjJXudjZDLmoO8ftmmPND8BVKUJzZnTc1bj6uCBkfsjBNV2DfsWyOfajyqfeesqHbte8XhMyV0c16Hg640xaNsfWih5w5bUCi+g1vn/PfPRVyFxNOtTZTPwLU07WtjnWbReAcZqzhV5eKfGZvxUzV1PxNZ3e2XIP5d7xG/geo3rXDvo8o3nhjZy5ytEbW/xrFAeOi5n38uvAVhSwovPvauC9L8uZnzoQ/woFvbM5V8XMe93zC4z39b/Q33Cgr1SBK3dR0Fydc2Ibm258z8iZ9+q2XcbNjR+9Fub/x/t5r0x8eNeN71OC5r2aqM67uT3iQF8Jga233yXN1ZRueI8/Z9t3TMIce8E4w9dxhn/EgV7jNH8pan56n2Z4x+ds/Unuhog51rSxuT1iQGfd1YSvc3WxP8fFnrHRj1wOXJMxx9Y4N7did92cAX1+pcI4w8ncz7FrB+KPW7Tj+5SQOXaPUR1aQc+cAb28Umfcz+eFzdVUfHjXj+97DkqZY5ybW7FnzoDe+F93tfWO7IkP7/rxfd8NKXOs67t801zEgM65q4HYORx25id3Z9DaRBRFYWQ2wmMgi2RKAkWkrpQUWgVFsOBOEdRdFmKD7cJ/oNi3GmE2DjiCClY0WoMSia2gItL443zPDN5FmPvwZcg9L3ff1ce595zzMrQxY97Z9r15f6HM609unwbzQz8M17eXJ/3kzGfKXPve3F0gc0puL2vN63NAr5v5gQBzvducad55JxefXSBz0nqNdz0f7PtDr/eeFyLM1+NZH8c7uca2APN6k9srQ937weXD4euAs9rfOdMgH+eCXnZy9ySY1/vSWgz2jw68oP/4cFhfPn81EWGu75V9HEFnnZw96g8FmNed1599zr77/N346NfrkLPadB5OTzr5OHcnF58TYF53cnsy+OIFfTCqL6sJ6Vyfjyv6OMbJNbYEmNft4fN377xS/rs8bN9uZ6tR4eO4emZXgDlRX5Z5I8Vc71ZUM9zrajO+KvY/721yW4op3vyQYr4eN2ffVfl6xh71vhBzM8fLQT2f/JZirq/Yk07VDA+djnpHiLmd/S/PHgU/r4ZyzHWHTjpB5+sZe9RVW4q5pf4yeOr55Jsc8zVlTnpFNcMe9W0x5pZ68G5uKJDVqI6rOOnMUZ8m9Y4Ac5qvgVOX8O00nWlKrzjpzFFP2gLMacJObrLMNxLmpLNJPd4WYU7Uw/XwxU+ZrEbbnUnpbP0ed0SY0xyH6uaKn0K+nbY7U7yz+z1OzooxDzq55UNh5mtJzGx3PrTFfSnmQSe3XMy3UzPDBjY+tO2IMSeth+fmxJnrHT6w8U2suirEnOYoOA8/eS7N/I7iO1g+tMVdCeZhJzexNxaabswENndoWxFnHlpym8gz1ytcYHOHNvVAnLnZ8OFQL+R3u9aXFRfY3Ptd9eSZWzcXCHV5325nVbHb3R3akrY88ywLhHo+/g3AfCNhAhsHnfb7NgDzLAsiuRUQOte3absz0Nn9fg2BeZaFkNyGAPfczDXXdnfvd3UTgXmW4Se3yQsI5ueVc7u793sPgjk+dYSsZmfVvd3d/UzShmBuNjyymyuGGDq3by3u7e7e730M5ll2/BaWOoZvt3PJa7vTfi/79+sgzI2HR6WejzA8nJnrZe9O291rv6u7IMwtdci7XowhspqdW8q93d3fLBup76AwN3cdkvoYRud6xwid/ULZ/b5qpR6r6BwKc+PhAVsakKxm51SkYiN07lWVh05WrgfDHJE6im+3s0o2joXu/v2MSjZhmGfpUyw3B5PV7NxJVMVvZjys3H0c5ml6hES9AOlep3PBy8YRdIrqVuptHOZpun8AQz3/iMR8zQqdQjoPnY/q1sp1gZinKUxyK2A6mb/TtTauIqR7WLlWG4i53fDFI4TBYr7RYmych5WLbiAxT1MMD4/k4cxcihgb52PlTreRmGNQB2O+cZqxcV5WLroBxTwFSG5jLOal0L1tHO33spVrqOh0G4p5mgq/tBZjkPdzEnqkGmUbR9t9PisX9bGYp+mxpIfPP0J5uFLojI3zeWC1UsdiLprcCqh8XgqdeVT1lnoXjLnZ8GLUR2g61935hU5WjlJbaxOM+f94+OX2cFqvtSivkY2bP7VFW2jMpagP36Mx11sRm9f8pZ5sojG31N0efvl1rtcTh9D9pd6DYy7w5laM8HSuV11C9y9oTtyEY26oHzg2/LL7djOnTjDFjGdB80/qHTzmC6ZePMHb7Vp3/gmdipnapB7dxWNuqS/uzQ3nt86a5mLkErp/F2uorzzGY257+EVpfYSoc71imDMNrKeVI6n3AZnv7T3nkttyZzUzl0jo89s46mJJ6q1NQOZ7e4t5cxtDMl9vkdCpga1V6j1E5pXUlz6r/WHnflbaCqIwgDPcPkCxpJcEpKS6NZAo2BYUdyEIUbPJQoLgxjfoIrMqWWZxhVIoYv1TDQhSsNKKUPtunZmYnJscvU3MTPLdeGfjA/w453xnJld1Znmh2y51UUY0r9edZ/igBfaWen8WBSt0+6VehDRX6jdO1YMryAwnZdF+ofMA7+1Amit1p5sbaJ3LFS8iutvb1VMFSHM110n92ZjnUi4KnV/LeRVM83r9V2hffw65XZ2Kxy7jrJb6TKfURQ3TXGd4o/58zJdFp9Bn6DLOYqnTT2i8UhrTXN3SuPjiKWihms+VPPrBjJ1C5+/q5hsnpb4Hak6bm1Vz0Nwu5ZIy118ysXd0O6X+oqfUxUdQc6V+GNje1UAznJQfRE+hv6BCt1jq5nNGo+6nQc3r9TvL6qj7uZR535jrTxZZobu4ofH2UM31XLepjtvbVXN3sq4ROl3Gdho8qrlSt5jhQe/bqbnTBSyhOyv1UhrV3Kb65S2s+VzJWaETurmhoSznVWHN7W1usLuaeVyjFKfvZQjdYZYTNVhzneaaNn4PB2y+LJylOL62UYPPFmDNVa3/CaY4t0uZy1JzZ+uawyznZXDNlfpNMKo5cJ3LjBeR4pxlOd3g13HNlfrhaOaQv3XunHe6ubtLcTzLUYN/XcY1N29uU5rh5OJrau4sxbnJctTg/TSu+Wib2wXuriblnE/N3VGK41mOGvwWsLnZ3KaxzuUWNXc3KY5nufCyLnaAzfVcbz5tV0Ouc7kiQiu6mxTHsxwt63qsA5ubDD9lu5oZ6LSiu0txhM4bfKkAbK7Vhzc/OKkDm+dKrLkzdOcNPoNsrtSHfmlFvodTJzOe5h7d4MUqsrl5aZ2iDCdXxVibO5U6NXijXkM2V/v6aXNadjUpN4w5NXeXhc4bPF3RiFQZ2Xy4za2Fbf4mJehaxmlz5w2+94pG+AVkcz3XB1QPrrDNc77ouZZx39x5gu+qZ6DNB05zAfQbi5T5TNd8PMk9eqxXoc31S2v8dzUpZ8c/0PkdPI11sQ5trms95m+p5mmNBrrDO/foBh8e66IGbW46fLz3c7kswgN9fM2dN/iueqoMbW42tzjndrmY6pqPqblH721GPbsGba43t2aMzbezxtzCtmZ1rPsFaHN9D/81rruazPkWBrqLsV5MQ5tHvLkFB+Dm+aKFge5krIsKtrne3OK5q+UrYsIDnY/1rnoV27zR+PztIXPs30yoM9sxH+9A5+h9Y72tvodtrtTPj1lvPwXf1eRS27x3oI8ZnY91E+aM+gK2eaNx/fdsv4e89RPxf/yGz4I2NyFukgOdGnz/27oQ69jmutiPzq6OmwZ8/6D15aQBbr4iRPgN3cJAtx7mxA64ua7226PTs4uLy5vzH3eNWJlbCHFOwpyYRzc3p379+7v6A28+T+aTC3E8zHVu5kg9Bub6xMu8HdwnE+J4mGMRXmwk5pbNKbhPKsTxsd6vPp+YOzCfeIh7QP1VWD0xt3A2Q+avcMxZhO+obybm1sxhgjuhtyM8qXv3+3piPuJ51zb3yNwEdwR0ivB96guJ+UhnIWwOEtxZhGfqe4n5CGep3xwiuPers0saUf2UmI/wrkaXMoDmpD7Tp155n5g/9f2czHEW9Kh1ndSLhcT8CSdXJHOoBT3ykqar7q8l5kOfbb9rjnUpM4C6Yc+WE/Mhz2JWk8fA/HH1VC0xH+osp2JjrtAfURfriflwVzLcHOZS5j/qL0m9mpgPevKzZP4S3zxKffdtYj7Qye3GzPwfu3a0ojYQhQGYU1govSvdJBAIwcRbBUnYuAGDdyoLq/GmF9KbfY++e3fWqf9kZhK1rtaZzHmFj/8/Z0Jk9a+CevDqzE+YOBDMvxphrlP/+3Tz1s786Ky9v081g8y71Kly5kemIiPNJfUfDfUwceYdMw4b5j/MMe9UX70689bJVuaaK+qPTJ2z+zNn3jIzn5Mz80fTzAX1B66OI57qxJlrJq0JZzs3fzDJnKvjrwrxwU7BqzNXJgtIfJ5z8+8mmTfVpc805L84c2mmPkmfZAw0b6grTzcK585cutrlp5qJ5viXRniwI+yDX878MMWAkzef5/f3n8x56uo5Rz+fnfnHpBFJJ5zJ5h/qeLpJ5xwFpTN/nzKg5jrHU81Ic6gLR7zAnj/33nySC+Q42002bzvnEPaemx9ibsEJp1NXFjsPe4/N05x069x4c6iLi11gX/3qrXmxEsjFdW68+Yc6zjm14mk776X5eEtqtR9OOMPNm+ecruK9WQ/NZ56u2k0/4ToXuxT2xWvPzLOFFHN71rmq3lbxFCU9Mh9HpKl2W9a5oK6r+EbHv/TGvNnsQrXbss6lxd5R8RT86oV5EVBrtduyzjsrXmIPY+vN41Ait7XadRWvDztFc6vNxxHJMbe32kV1fKjRhd2rEmvN08rTxZx/kLHUHBWvCTvYR89Wmk9GINfE3MZqV8L+wMOunPFEg5dn68wn0wGRcrTzmD9YHPPmFS+EnX2gk9mtMpfI2Sc4MeYWXu3asH9j9xw/49HxDXZrzEHeaHZ+tLML7pvVMYe6GHbxoAP7KLHCPB2BXDjgeMytvuB09xzCzjpeZffyufHmu9yTyHmzI+YWX3DdYdd3PPlRbLR5HPmkb/a+xVzc7PyMR8fL7PRWGGtevJFCjmZnR3svtrku7Oj4FvbgJTHQPJ0GLeRo9n7FXN/xWO0SuxdlhplnkdckxzLvabNL6rzjsdrBjllsEmPMJ5sFacnZMkez99Nc2/EKO+JeGmFeRl4Led+bXdPxx9kpGM3v3Hw3CugIeY+bXVE/jZ3eNsndmqebNzqBvM/NroadX3RH2P3tOrlD88l663eT8/ut583estpVdtV9mNyVeTrcepK4Su6Wuax+Jjv54Wx+J+a7WejTWeTOvBF2PbvenZZV9t/Ns2pJenE9uYt5C/v3Jrsad8ygHj79N/PxsB4QtYf8QO7utxMPeX7Stccdgc+L5ObmaZEj4u0h359vjvwkdlbyLO5g18cd8Ounm5mna4DrQw7y95CzYnfkJ7Oj5dvjjgnqWfz72ubZrA4I0xZy3uuO/OzdjuWOuHe7k7fIN/Hv65jHm3zhUac4Qo5V7s63i9hR83DXjb+MpsXuE813xTRa+kQd4qh1R34JO77ScXfhmO+AR+jrapglF5mn2bCqEW89OGq9EfLD1zdHfh47X+487my7n+oO+2UYjYZlnJ5hnsblcBSFS2h3RxzibJPzkLNV7sj/9SudEnfmjv0O+O7xV8uwzqvpZl2UZRw/vc+H+dN4PI7jrCzWm2mV1+FyhSI/LeJcXAm5+/p2Afv+5c7irnMH/I0G4DpxFvL9q9yRX8IuxR3uj3C/CTzAIf4I8WbIHfnlcde4S4G/pjy81YhL4i7kV4i76M4Dz+GZPOZTueENcB5xQdyF/BbuCDyDhzzms7jhDXAWcSd+zflyqHm4I/CAh3xjLsCGtwCOiO/FUeuO/Bpx5+5sv/PAA16Uh70yR5mhLXlzcESc7fG9uAs55nruLPAqPJdn9LAHfvcAG9qMm3kr4DziTvxPe3WQ2yAQBFFUmivk/ndNFzR6UWSWNiD337J8Xcz74/538AVPHn3s4euMGXZr4+adf7qJj/hnWi8G740veaMv+8Kn7wZEmXVhR9u8491v+L+Jzzv+qRb3Hb7kTR59Zk8//kKcWpp1xo3bwMt7Ax/xC1pn8Ojbnr5OYt3auF+Bj/gFtTv4TR49+/Af/Uj5cFDTxt3eDT4Tv7QFnjz62O/6/CXSbV3auHkX+Ez8Fq1EPvS9+rbH7wAEGnW0j3WHm/eA3yjy6Nt+18cvoY51bxv3eN838qGPfePzP05A7Uw62Jt2uMf7ES30jR/9zgWIc1sHu1vj/aBWgs//vEjDHu7HtpLiKYH+Du1fGcWqXm7s+mIAAAAASUVORK5CYII=)';    \tbutton.addEventListener('click', function(){ dataGet.onData(button.className,parent.innerHTML)});\n    \tvar spn=document.createElement('span');\n    \tspn.className='newspan';\n    \tspn.appendChild(button);\n    \titems[i].appendChild(spn);\n    }\n}}\n})()");
            }
        });
    }

    private void addButtonBatch(final String str, final String[] strArr, final String str2, int i2) {
        this.webView.post(() -> {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                WebView webView = this.webView;
                webView.loadUrl("javascript:(function() { var parents=document.getElementsByClassName('" + str + "');var parent=parents[" + i + "];var items=parent.getElementsByClassName('" + str2 + "'); \nfor (var i = 0; i < items.length; i++) { \n\tif(items[i].getElementsByClassName('newspan').length<1){\n\t\tvar button=document.createElement('button');\n    \tbutton.style.width = '28px';\n    \tbutton.style.height = '28px';\n    \tbutton.style.marginTop = '6px';\n    \tbutton.style.outline = 'none';\n    \tbutton.style.border = 'none';\n    \tbutton.style.backgroundSize = 'cover';\n    \tbutton.style.backgroundColor = '#00000000';\n    \tbutton.className='button_'+" + i + "+'_'+i;\n    \tbutton.style.backgroundImage= 'url(data:image/png;base64,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)';    \tbutton.addEventListener('click', function(){ dataGet.onDataBatch(button.className,parent.innerHTML)});\n    \tvar spn=document.createElement('span');\n    \tspn.className='newspan';\n    \tspn.appendChild(button);\n    \titems[i].appendChild(spn);\n    }\n}})()");
            }
        });
    }

    private void downloadUrl(String instaApiCallUrl, String instaPageUrl) {
        try {
            this.activityVideos.foundInstaURL(instaApiCallUrl, instaPageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @JavascriptInterface
    public void onAccountBatchPostLoading(String[] strArr) {
        addButtonBatch(INSTA_ACCOUNT_BATCH_POSTS, strArr, "_aamu _aat0", 1);
        addButtonBatch(INSTA_ACCOUNT_BATCH_POSTS, strArr, "_aamu _ae3_", 1);
    }

    @JavascriptInterface
    public void onAccountPostLoading(String[] strArr) {
        addButtonBatch(INSTA_ACCOUNT_POSTS, strArr, "_aamu _aat0", 1);
        addButtonBatch(INSTA_ACCOUNT_POSTS, strArr, "_aamu _ae3_", 1);
    }

    @JavascriptInterface
    public void onData(String str, final String str2) {
        try {
            if (SystemClock.elapsedRealtime() - this.lastTimeClicked >= 1000) {
                this.lastTimeClicked = SystemClock.elapsedRealtime();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    String substringBefore = StringUtils.substringBefore(StringUtils.substringAfterLast(str, "href=\"/p/"), "/");
                    String pathparse = pathparse("https://www.instagram.com/p/", substringBefore, "/?__a=1&__d=dis");
                    downloadUrl(pathparse, "https://www.instagram.com/p/" + substringBefore + '/');

                }, 100L);
            }
        } catch (Exception unused) {
            throw new RuntimeException(unused);
        }
    }

    @JavascriptInterface
    public void onDataBatch(String str, String str2) {
        try {
            if (SystemClock.elapsedRealtime() - this.lastTimeClicked >= 1000) {
                this.lastTimeClicked = SystemClock.elapsedRealtime();
                String substringBefore = StringUtils.substringBefore(StringUtils.substringAfterLast(str2, "href=\"/p/"), "/");
                downloadUrl("https://www.instagram.com/p/" + substringBefore + "/?__a=1&__d=dis", "https://www.instagram.com/p/" + substringBefore + '/');
            }
        } catch (Exception unused) {
            throw new RuntimeException(unused);
        }
    }

    @JavascriptInterface
    public void onFeedBatchPostLoading(String[] strArr) {
        addButtonBatch(INSTA_BATCH_POSTS, strArr, "_aamu _aat0", 1);
        addButtonBatch(INSTA_BATCH_POSTS, strArr, "_aamu _ae3_", 1);
    }

    @JavascriptInterface
    public void onLoading(String[] strArr) {
        addButton(INSTA_FEED, strArr, "_aamu _aat0");
        addButton(INSTA_FEED, strArr, "_aamu _ae3_");
    }

    @JavascriptInterface
    public void onLoadingPublic(String[] strArr) {
        addButton(INSTA_PUBLIC_POSTS, strArr, "_aamu _aat0");
        addButton(INSTA_PUBLIC_POSTS, strArr, "_aamu _ae3_");
    }

    @JavascriptInterface
    public void onSearchPostLoading(String[] strArr) {
        addButton(INSTA_SEARCH_POST, strArr, "_aamu _aat0");
        addButton(INSTA_SEARCH_POST, strArr, "_aamu _ae3_");
    }

    @JavascriptInterface
    public void onSinglePostLoading(String[] strArr) {
        addButton(INSTA_SINGLE_POST, strArr, "_aamu _aat0");
        addButton(INSTA_SINGLE_POST, strArr, "_aamu _ae3_");
    }
    public static String m(String str, String str2, String str3) {
        return str + str2 + str3;
    }
    public String pathparse(final String str, final String str2, final String str3) {
        return m(str, str2, str3);
    }
}
