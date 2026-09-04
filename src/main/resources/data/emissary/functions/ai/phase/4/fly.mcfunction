#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s if entity @a[tag=emissary,tag=flytarget,distance=41..] run function emissary:attacks/phase4/accelerate
execute at @s if entity @a[tag=emissary,tag=flytarget,distance=..40] if score FlySpeed em.value matches 5.. run function emissary:attacks/phase4/decelerate
execute if score FlySpeed em.value matches 1 run function emissary:ai/phase/4/__generated__/block/754
execute if score FlySpeed em.value matches 2 run function emissary:ai/phase/4/__generated__/block/755
execute if score FlySpeed em.value matches 3 run function emissary:ai/phase/4/__generated__/block/756
execute if score FlySpeed em.value matches 4 run function emissary:ai/phase/4/__generated__/block/757
execute if score FlySpeed em.value matches 5 run function emissary:ai/phase/4/__generated__/block/758
execute if score FlySpeed em.value matches 6 run function emissary:ai/phase/4/__generated__/block/759
execute if score FlySpeed em.value matches 7 run function emissary:ai/phase/4/__generated__/block/760
execute if score FlySpeed em.value matches 8 run function emissary:ai/phase/4/__generated__/block/761
execute if score FlySpeed em.value matches 9 run function emissary:ai/phase/4/__generated__/block/762
execute if score FlySpeed em.value matches 10 run function emissary:ai/phase/4/__generated__/block/763
execute at @s run function emissary:ai/phase/4/__generated__/block/764