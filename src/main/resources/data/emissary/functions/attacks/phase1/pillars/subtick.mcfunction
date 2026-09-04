#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.value 1
execute if score @s em.value matches 300.. run tag @s add end
execute as @s[tag=cloud] at @s run function emissary:attacks/phase1/pillars/__generated__/block/307
execute as @s[tag=flame] at @s run function emissary:attacks/phase1/pillars/__generated__/block/308
execute as @s[tag=end] at @s run function emissary:attacks/phase1/pillars/end
execute as @a[tag=em.impact.cloud] at @s run function emissary:attacks/phase1/pillars/impact_cloud
execute as @a[tag=em.impact.flame] at @s run function emissary:attacks/phase1/pillars/impact_flame