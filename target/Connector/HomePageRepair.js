

$("document").ready(()=>{
    fetch("homepagedetailsprovider").then(data => data.json())
        .then(res =>{
            changeVideoBackground(res)
            return res
        }).then(res1 =>{
            repairDetails(res1)
            return res1
        }).then(res2 =>{
            repairFooter(res2)
            return res2
        }).then(res3 =>{
            repairSlideShow(res3)
            return res3
        }).then(x=>{
            showSlides(Index)
            createArrayForSlideIds(x.albums.length)
        }).catch(console.error)
})

function changeVideoBackground(json){
    let usrname = json.personalData.username;
    let cvr = json.cvr_picture;

    let vid = json.data.videoAsCover;
    if (vid === true){
        let video = $("<video>",{
            id : "myVideo",
            style : "min-width: 100%",
            autoplay : true,
            muted : "muted",
            loop : true
        });
        $("<source>",{
            src : "images/"+usrname+"/"+cvr,
            type : "video/mp4"
        }).appendTo(video);

        video.appendTo(document.querySelector("#HEADER"))
        console.log("video appended")
    }else {
        $("<img>",{
            src : "images/"+usrname+"/"+cvr,
            style: "min-width:100%"
        }).appendTo($("#HEADER"))
    }
}

function repairDetails(json){
    let mast = '<p><i class="fas fa-user-graduate"></i> studied'
    let grad = '<p><i class="fas fa-graduation-cap"></i>studied'
    let second = '<p><i class="fas fa-university"></i>studied'
    let prim = '<p><i class="fas fa-school"></i>studied'

    console.log("q4::::"+json.data.edu.q4)
    console.log("q3::::"+json.data.edu.q3)
    console.log("q2::::"+json.data.edu.q2)
    if (json.data.edu.q4 !== ""){
        let edu = mast+" "+json.data.edu.q4+" at "+json.data.edu.c4+"</p>"
        $("<div>",{
            class : "profession",
        }).html(edu).appendTo($("#DETAILS"))
    }
    if (json.data.edu.q3 !==""){
        let edu = grad+" "+json.data.edu.q3+" at "+json.data.edu.c3+"</p>"
        $("<div>",{
            class : "profession",
        }).html(edu).appendTo($("#DETAILS"))
    }
    if (json.data.edu.q2 !==""){
        let edu = second+" "+json.data.edu.q2+" at "+json.data.edu.c2+"</p>"
        $("<div>",{
            class : "profession",
        }).html(edu).appendTo($("#DETAILS"))
    }
    if (json.data.edu.q1!==""){
        let edu = prim+" "+json.data.edu.q1+" at "+json.data.edu.c1+"</p>"
        $("<div>",{
            class : "profession",
        }).html(edu).appendTo($("#DETAILS"))
    }
}

function repairFooter(json){
    let fb = json.data.social.facebook
    let twit = json.data.social.twitter
    let link = json.data.social.linkedin
    let insta = json.data.social.instagram
    let tube = json.data.social.youtube
    let quora = json.data.social.qoura
    let overflow = json.data.social.overflow

    let footer = document.querySelector("#FOOTER")
    if (fb){
        let anchor = $("<a>",{
            href : fb,
            target : "_blank"
        }).appendTo(footer)
        $("<img>",{
            src : "https://image.flaticon.com/icons/png/512/174/174848.png",
            width : "30px",
            height : "30px"
        }).appendTo(anchor)
    }
    if (twit){
        let anchor = $("<a>",{
            href : twit,
            target : "_blank"
        }).appendTo(footer)
        $("<img>",{
            src : "https://image.flaticon.com/icons/png/512/733/733579.png",
            width : "30px",
            height : "30px"
        }).appendTo(anchor)
    }
    if (link){
        let anchor = $("<a>",{
            href : link,
            target : "_blank"
        }).appendTo(footer)
        $("<img>",{
            src : "https://image.flaticon.com/icons/png/512/174/174857.png",
            width : "30px",
            height : "30px"
        }).appendTo(anchor)
    }
    if (insta){
        let anchor = $("<a>",{
            href : insta,
            target : "_blank"
        }).appendTo(footer)
        $("<img>",{
            src : "https://image.flaticon.com/icons/png/512/174/174855.png",
            width : "30px",
            height : "30px"
        }).appendTo(anchor)
    }
    if (tube){
        let anchor = $("<a>",{
            href : tube,
            target : "_blank"
        }).appendTo(footer)
        $("<img>",{
            src : "https://image.flaticon.com/icons/png/512/174/174883.png",
            width : "30px",
            height : "30px"
        }).appendTo(anchor)
    }
    if (quora){
        let anchor = $("<a>",{
            href : quora,
            target : "_blank"
        }).appendTo(footer)
        $("<img>",{
            src : "https://image.flaticon.com/icons/png/512/174/174865.png",
            width : "30px",
            height : "30px"
        }).appendTo(anchor)
    }
    if (overflow){
        let anchor = $("<a>",{
            href : overflow,
            target : "_blank"
        }).appendTo(footer)
        $("<img>",{
            src : "https://cdn.sstatic.net/Sites/stackoverflow/company/img/logos/so/so-icon.svg?v=f13ebeedfa9e",
            width : "30px",
            height : "30px"
        }).appendTo(anchor)
    }
}


function repairSlideShow(json){
    let username =json.personalData.username
    let main = document.querySelector("#MAIN");
    for (z in json.albums){

        let alb = json.albums[z]

        let carousel = $("<div>",{
            "id" : `myCarousel${z}`,
            "class" : "carousel slide",
            "data-ride" : "carousel",
            "data-interval" : "false"
        })

        let ul = $("<ul>",{
            class:"carousel-indicators"
        })

        let div = $("<div>",{
            class : "carousel-inner"
        })
        let className = alb.className
        let media = alb.media

        for (i=1;i<=5;i++){
            if (media[`${i}`]){
                let type = (media[`${i}`])[1]

                let item = $("<div>",{
                    class : i===1?"carousel-item active":"carousel-item"
                })

                if (type.toString().includes("video")){
                    $("<video>",{
                        style :"width:100%",
                        class : alb.className,
                        src : `images/${username}/${alb.Album_ID}/${(media[`${i}`])[0]}`,
                        controls : "controls"
                    }).appendTo(item)
                }else{
                    $("<img>",{
                        style :"width:100%",
                        class : alb.className,
                        src : `images/${username}/${alb.Album_ID}/${(media[`${i}`])[0]}`
                    }).appendTo(item)
                }

                let li = $("<li>",{
                    "class" : i===1?`item${i} active`:`item${i}`,
                    "data-slide-to" : `${i-1}`,
                    "data-target" : `#myCarousel${z}`
                })

                li.appendTo(ul)
                item.appendTo(div)

            }

            div.appendTo(carousel)
        }

        $("<a>",{
            "class" : "carousel-control-prev",
            "href" : `#myCarousel${z}`,
            "data-slide" : "prev"
        }).html(`<span class='carousel-control-prev-icon'></span>`).appendTo(carousel)

        $("<a>",{
            "class" : "carousel-control-next",
            "href" : `#myCarousel${z}`,
            "data-slide" : "next"
        }).html(`<span class='carousel-control-next-icon'></span>`).appendTo(carousel)

        carousel.appendTo(main)
        console.log("Executeddddddddddd")
    }

}
