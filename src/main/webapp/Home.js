window.onload = function() {
    let clockTimer = setInterval(clock,1000);

}
function clock()
{
    let dt = new Date();
    document.getElementById('time').innerText = dt.toLocaleTimeString();
    document.getElementById('date').innerText = dt.toDateString();
}

// function for top navigation bar
function myFunction() {
    let x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}

