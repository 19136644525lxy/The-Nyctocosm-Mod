#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score HurtTime em.value run data get entity @s HurtTime
execute unless score HurtTime em.value matches 1.. run function emissary:attacks/phase4/divine_blast/impact