#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s resonancerange 1
particle sonic_boom ~ ~ ~ 0.1 0.1 0.1 0 1 force
execute if score @s resonancerange matches 70.. run tag @s add endraycast
scoreboard players operation Temp s.damage = @s s.damage
execute positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,dx=0,tag=!resonancehit] at @s run function spells:resonance/__generated__/block/252
function nyctocosm:portal/light
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function spells:resonance/__generated__/block/258
execute as @s[tag=!endraycast] unless entity @e[tag=reflector,tag=!matchedid,tag=spell,distance=..3] positioned ^ ^ ^1 run function spells:resonance/raycast
execute as @s[tag=!endraycast] rotated as @e[tag=reflector,tag=spell,tag=!matchedid,distance=..3,limit=1,sort=nearest] positioned ^ ^ ^1 run function spells:resonance/raycast
execute as @s[tag=endraycast] run function spells:resonance/__generated__/block/261