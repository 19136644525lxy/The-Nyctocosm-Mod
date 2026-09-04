#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.value 1
execute if score @s em.value matches 15 rotated ~ 90 run function effects:shockwave/meteor_shower
execute if score @s em.value matches 15 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 30 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 45 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 60 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 75 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 90 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 105 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 120 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 135 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 150 run function emissary:attacks/phase1/meteor_shower/shoot
execute if score @s em.value matches 160.. run kill @s