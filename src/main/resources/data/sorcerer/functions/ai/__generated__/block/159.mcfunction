#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset Nullpoint ns.health
function sorcerer:fall
scoreboard players add Sorcerer ns.phase 1
execute if score Sorcerer ns.phase matches 2 as @a[tag=ns.damagedsorcerer] at @s run function music:sorcerer/phase/2/queue
execute if score Sorcerer ns.phase matches 4 as @a[tag=ns.damagedsorcerer] at @s run function music:sorcerer/phase/4/queue
execute if score Sorcerer ns.phase matches 6 as @a[tag=ns.damagedsorcerer] at @s run function music:sorcerer/phase/6/queue