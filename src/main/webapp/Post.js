//$("#close-post").click('PostCloser');

function PostOpener() {
    $("#upload").show();
    $("#PostLoadingAnimation").hide();
    $("#close-post").show();
    $('#PostModal').fadeIn();
    $(".post-footer p").hide();
}

function PostCloser() {
    $("#PostModal").fadeOut();
    $("#PostModal img").attr('src','http://icons.iconarchive.com/icons/ccard3dev/dynamic-yosemite/1024/Preview-icon.png');
    $('#PostModal video').attr('src', ' ');
    $('#PostModal video').hide();
    $('#PostModal img').show();
    $(".previewName").html('No file selected');
    files = document.getElementsByClassName('fileInput');
    for (i=0;i<files.length;i++)
    {
        files[i].value ="";
    }
}

function preview(input,event)
{
    id = input.getAttribute('id');
    prv = document.getElementsByClassName('preview');
    console.log(id);
    switch (id) {
        case 'file1': {

            $(".p1").html(input.files[0].name);
            if (input.files[0].type.toString().includes('video'))
            {
                $("#preview1").hide();
                $("#part1>label>video").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#part1>label>video").show();
            }
            else {
                $("#part4>label>video").attr('src',' ');
                $("#part1>label>video").hide();
                $("#preview1").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#preview1").show();
            }
            break;
        }
        case 'file2': {
            $(".p2").html(input.files[0].name);
            if (input.files[0].type.toString().includes('video'))
            {
                $("#preview2").hide();
                $("#part2>label>video").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#part2>label>video").show();
            }
            else {
                $("#part2>label>video").attr('src',' ');
                $("#part2>label>video").hide();
                $("#preview2").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#preview2").show();
            }
            break;
        }
        case 'file3': {
            $(".p3").html(input.files[0].name);
            if (input.files[0].type.toString().includes('video'))
            {
                $("#preview3").hide();
                $("#part3>label>video").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#part3>label>video").show();
            }
            else {
                $("#part3>label>video").attr('src',' ');
                $("#part3>label>video").hide();
                $("#preview3").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#preview3").show();
            }
            break;
        }
        case 'file4': {
            $("#preview4").attr('src',URL.createObjectURL(event.target.files[0]));
            $(".p4").html(input.files[0].name);
            if (input.files[0].type.toString().includes('video'))
            {
                $("#preview4").hide();
                $("#part4>label>video").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#part4>label>video").show();
            }
            else {
                $("#part4>label>video").attr('src',' ');
                $("#part4>label>video").hide();
                $("#preview4").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#preview4").show();
            }
            break;
        }
        case 'file5': {
            $(".p5").html(input.files[0].name);
            if (input.files[0].type.toString().includes('video'))
            {
                $("#preview5").hide();
                $("#part5>label>video").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#part5>label>video").show();
            }
            else {
                $("#part5>label>video").attr('src',' ');
                $("#part5>label>video").hide();
                $("#preview5").attr('src',URL.createObjectURL(event.target.files[0]));
                $("#preview5").show();
            }
            break;
        }
    }
    console.log(URL.createObjectURL(event.target.files[0]));
}


function PostSubmit(event,allData) {
    event.preventDefault();
    let inputs = document.getElementsByClassName('fileInput');
    let empty =true;
    for (i=0;i<inputs.length;i++)
    {
        if (inputs[i].files.length >0)
        {
            empty = false;
        }
    }
    if (empty)
    {
        $(".post-footer p").show();
    }
    else {
        $(".post-footer p").hide();

        let data = new FormData(allData);
        upload(data);
    }
}

function upload(files) {
    showPostLoader();
    uploader = new XMLHttpRequest();
    uploader.open('POST','PostServlet','true');
    uploader.onload = function () {
        alert(uploader.responseText);
        PostCloser();
    };
    uploader.send(files);
}

function showPostLoader() {
    $("#upload").hide();
    $("#close-post").hide();
    $("#PostLoadingAnimation").show();

}