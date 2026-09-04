#built using mc-build (https://github.com/mc-build/mc-build)

execute if score RANGE em.value matches 10 rotated ~ ~-90 run function effects:shockwave/meteor_shower
execute if score RANGE em.value matches 30 rotated ~ ~-90 run function effects:shockwave/meteor_shower_small
execute if score RANGE em.value matches 40 rotated ~ ~-90 run function effects:shockwave/meteor_shower_small
execute positioned ^-1.2586072936146786 ^-0.8160316663340547 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^0.6121230927200879 ^1.3694178760914415 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^1.5 ^0 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^1.44744904273817 ^-0.39356228055589315 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-1.4286194706227344 ^-0.457215931653325 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-1.0004070924783928 ^1.1176697407190233 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^-0.6721104241937552 ^1.3409949954008367 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^1.2934783084315258 ^-0.7595484616646382 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-0.16558086575857134 ^-1.4908329808850629 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^-0.5509369956820447 ^-1.3951589252801426 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-0.2967203610064023 ^1.4703594891604674 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^1.2212714557898428 ^0.8709167763184714 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^1.4069921281791622 ^0.5199741832455455 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-0.8976901035867871 ^-1.2017289536007456 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-1.463443969192856 ^0.3291378875685071 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^-1.3258162097735577 ^0.7015777775141384 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^1.4941277563075823 ^0.13259806873129723 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^0.7307815125105088 ^-1.3099459458209919 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^0.36148245792788797 ^-1.455792029312708 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-1.0952912357184568 ^1.024859555723776 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^0.4886719583027221 ^1.4181677323816753 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^0.8436433901501157 ^1.240268450883122 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-0.03314492891802591 ^-1.4996337598517242 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^-1.3826093698189736 ^-0.5817141312438442 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
execute positioned ^-1.4867982326328102 ^-0.1985724438081779 ^ run particle firework ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^1.1395024650108372 ^0.9754661102438288 ^ run particle flame ~ ~ ~ ^ ^ ^10000000 0.0000001 0 force
execute positioned ^1.355557667955921 ^-0.6422331421266774 ^ run particle wax_on ~ ~ ~ 0 0 0 0 1 force
scoreboard players reset TEMP em.value
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function emissary:attacks/phase4/divine_blast/__generated__/block/718
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase4/divine_blast/__generated__/block/719
execute as @s[tag=!reflected] positioned ^ ^ ^1 run function emissary:attacks/phase4/divine_blast/raycast
execute as @s[tag=reflected] rotated as @e[tag=spell,tag=reflector,distance=..3] positioned ^ ^ ^1 run function emissary:attacks/phase4/divine_blast/raycast