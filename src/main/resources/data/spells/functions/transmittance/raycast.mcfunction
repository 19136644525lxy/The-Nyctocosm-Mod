#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add self
scoreboard players remove TRANGE value 1
execute unless block ^ ^ ^1 #nyctocosm:passthrough run tag @s add endraycast
execute if entity @e[type=#nyctocosm:livingthings,tag=!self,distance=..2] run tag @s add endraycast
execute if score TRANGE value matches ..0 run tag @s add endraycast
tag @s remove self
execute as @s[tag=!endraycast] positioned ^ ^ ^1 run function spells:transmittance/raycast
execute as @s[tag=endraycast] positioned ~ ~ ~ run function spells:transmittance/__generated__/block/478