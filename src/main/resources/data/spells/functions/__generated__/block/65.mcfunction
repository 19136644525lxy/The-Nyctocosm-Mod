#built using mc-build (https://github.com/mc-build/mc-build)

kill @s[tag=!origin]
execute as @s[tag=origin] run function spells:__generated__/block/66
particle minecraft:crit ~ ~ ~ 0.5 0.5 0.5 1 50 force
particle minecraft:enchanted_hit ~ ~ ~ 0.2 0.2 0.2 1 30 force
particle minecraft:firework ~ ~ ~ 0.2 0.2 0.2 0.3 50 force
playsound nyctocosm:spells.impact.forbiddance master @a[distance=..50] ~ ~ ~ 5