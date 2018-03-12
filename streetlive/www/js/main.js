$(document).ready(function(){
	$(".owl-carousel").owlCarousel({
		dots: false,
		scrollPerPage: true,
		slideBy: 3,
		loop: false,
		navText: ["<",">"],
		responsive:{
			0: {
				items: 1,
				nav: false
			},
			720: {
				items: 2,
				nav: true
			},
			1080: {
				items: 3,
				nav: true
			}
		},
		autoplay: false
	});
	$(".owl-carousel").trigger('stop.owl.autoplay');
});

const headtext = [
	"Une carte interactive",
	"Un suivi des artistes",
	"Une communauté",
	"Des démarches simplifiées"
];

const subtext = [
	"En temps réel, découvrez tous les artistes ou spectacles autour de vous, et renseignez vous-même sur la carte des artistes lorque vous en rencontrez",
	"Vous pouvez suivre vos artistes ou styles favoris pour ne rien rater de leurs futurs événements, faires des dons aux artistes que vous appréciez ou leur laisser un message, via l'application",
	"Notre solution permet aux artistes de mettre en avant leur talent, leur spectacle, en informant les personnes proches intéréssées par leur style",
	"Dans les villes partenaires, les artistes peuvent faire une demande d'autorisation de jouer en quelques clics sur l'application. Nous nous chargeons des démarches avec la mairie !"
];

function changeText(index)
{
	$("#head-text").text(headtext[index]);
	$("#sub-text").text(subtext[index]);
	$(".icon.major").removeClass("activated");
	$(".point-icon:nth-child(" + (index + 1) + ") span").addClass("activated");
}