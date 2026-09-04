#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.timer 1
execute at @s run function emissary:attacks/phase4/divine_blast/raycast
execute if score @s em.timer matches 26.. run kill @s