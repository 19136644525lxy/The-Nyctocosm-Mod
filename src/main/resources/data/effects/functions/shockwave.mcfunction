#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=emitter] at @s run function effects:__generated__/block/0
execute as @e[tag=emitter] run function effects:__generated__/block/1
schedule function effects:__generated__/sequence/1 2t replace
schedule function effects:__generated__/sequence/2 4t replace