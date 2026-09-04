#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score HurtTime em.value run data get entity @s HurtTime
execute as @s unless score HurtTime em.value matches 1.. run function emissary:ai/phase/4/__generated__/block/770