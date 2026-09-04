#built using mc-build (https://github.com/mc-build/mc-build)

function sorcerer:rise
scoreboard players add Sorcerer ns.phase 1
execute as @a[tag=ns.damagedsorcerer] at @s run function music:sorcerer/phase/3/queue