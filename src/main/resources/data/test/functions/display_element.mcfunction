#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add testelement value 1
execute if score testelement value matches 7.. run scoreboard players set testelement value 1
execute if score testelement value matches 1 run execute as @e[tag=emitterboy] at @s positioned ~ ~1.625 ~ run function effects:elements/dream/transmutate/play
execute if score testelement value matches 2 run execute as @e[tag=emitterboy] at @s positioned ~ ~1.625 ~ run function effects:elements/radiance/transmutate/play
execute if score testelement value matches 3 run execute as @e[tag=emitterboy] at @s positioned ~ ~1.625 ~ run function effects:elements/resonance/transmutate/play
execute if score testelement value matches 4 run execute as @e[tag=emitterboy] at @s positioned ~ ~1.625 ~ run function effects:elements/nightmare/transmutate/play
execute if score testelement value matches 5 run execute as @e[tag=emitterboy] at @s positioned ~ ~1.625 ~ run function effects:elements/void/transmutate/play
execute if score testelement value matches 6 run execute as @e[tag=emitterboy] at @s positioned ~ ~1.625 ~ run function effects:elements/dissonance/transmutate/play