#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["soundemitter"]}
execute as @e[tag=soundemitter] run function nyctocosm:player/__generated__/block/11
execute at @e[tag=soundemitter] run playsound minecraft:block.deepslate.step master @s ~ ~ ~ 5
kill @e[tag=soundemitter]