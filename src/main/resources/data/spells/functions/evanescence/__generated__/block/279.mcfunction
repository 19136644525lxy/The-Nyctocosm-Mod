#built using mc-build (https://github.com/mc-build/mc-build)

execute rotated ~ -90 run function effects:elements/resonance/transmutate/play
execute rotated ~ -90 run function effects:elements/void/transmutate/play
execute in minecraft:overworld run tp @s ~ ~ ~
execute store result entity @s Pos[0] double 1 run scoreboard players get TempX value
execute store result entity @s Pos[1] double 1 run scoreboard players get TempY value
execute store result entity @s Pos[2] double 1 run scoreboard players get TempZ value
execute at @s run tp @a[tag=caster] ~ ~ ~ ~ ~
kill @s