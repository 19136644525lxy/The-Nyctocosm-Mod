#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add SerpentLaser timer 1
execute if score SerpentLaser timer matches 100.. run function serpent:attack/laser/__generated__/block/207
execute as @e[tag=serpent,tag=target] at @s run tp @s ^ ^ ^0.5
tag @s add damagetemp
execute at @s facing entity @e[tag=serpent,tag=target] feet run function serpent:attack/laser/raycast