
let buttonPasswordForm = document.getElementById('showNewPasswordForm');
buttonPasswordForm.onclick = function () {
    let passwordForm = document.getElementById('password-form');
    passwordForm.classList.toggle('hidden-form');
}


let sharePasswordButtons = document.getElementsByClassName('share-password-btn');

for (let i = 0; i < sharePasswordButtons.length; i++) {
    sharePasswordButtons[i].addEventListener('click' , function (){
        let value = sharePasswordButtons[i].getAttribute('value');
        document.getElementById('password-id').value = value;
        document.querySelector('.bg-modal').style.display = 'flex';
    })
}

document.querySelector('.close').addEventListener('click', function (){
    document.querySelector('.bg-modal').style.display = 'none';
})
document.querySelector('.close-edit-form').addEventListener('click', function (){
    document.querySelector('.bg-modal-edit').style.display = 'none';
})

let url = new URL(window.location.href);

let error_share_password = url.searchParams.get('error-share-password')
let show_password_edit_from = url.searchParams.get('edit-password')

if(error_share_password != null){
    document.querySelector('.bg-modal').style.display = 'flex';
}

if(show_password_edit_from != null){
    document.querySelector('.bg-modal-edit').style.display = 'flex';
}

